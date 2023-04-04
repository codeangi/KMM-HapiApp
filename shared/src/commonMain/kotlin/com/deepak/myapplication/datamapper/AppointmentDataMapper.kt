package com.deepak.myapplication.datamapper

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AppointmentDataMapper {

    companion object {
        const val PRACTITIONER_START_DATE = "practitioner-practice-start"
        const val PRACTITIONER_EDUCATION = "practitioner-education"
        const val PRACTITIONER_RESIDENCY = "practitioner-residency"
        const val RESOURCE_CARE_TEAM = "CareTeam"
        const val RESOURCE_ORGANIZATION = "Organization"
        const val RESOURCE_PRACTITIONER = "Practitioner"
        const val PRACTITIONER_ABOUT = "practitioner-about"
    }

    fun getPatientAppointmentSchedules(appointmentsResponse: AppRequest, isToday: Boolean = false) : AppRequest {
        val dataList = mutableListOf<AppointmentScheduleData>()
        if (appointmentsResponse is AppRequest.Result<*> && appointmentsResponse.result is AppointmentResp) {
            appointmentsResponse.result.let { response ->
                response.entry?.forEach { entry ->
                    var appointmentDate = ""
                    entry.resource.start?.let {
                        val localTimeDate =
                            Instant.parse(entry.resource.start.substring(0, 19) + "Z")
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        val month = localTimeDate.month.name.substring(0, 3)
                        val dateOfMonth = localTimeDate.dayOfMonth.toString()
                        val dayOfWeek = localTimeDate.dayOfWeek.name
                        val time =
                            "${localTimeDate.time.hour % 12}:${localTimeDate.time.minute} ${if (localTimeDate.time.hour % 12 == 0) "AM" else "PM"}"

                        appointmentDate = "${dayOfWeek}, $month $dateOfMonth at $time"
                        if (isToday) {
                            if (localTimeDate.date == Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) {
                                dataList.add(
                                    AppointmentScheduleData(
                                        entry.resource.reasonCode?.firstOrNull()?.coding?.firstOrNull()?.display,
                                        appointmentDate,
                                        "Dr. Leslie Crona, RN",
                                        "Measured Wellness Llc"
                                    )
                                )
                            }
                        } else {
                            if (localTimeDate.date != Clock.System.now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            ) {
                                dataList.add(
                                    AppointmentScheduleData(
                                        entry.resource.reasonCode?.firstOrNull()?.coding?.firstOrNull()?.display,
                                        appointmentDate,
                                        "Dr. Leslie Crona, RN",
                                        "Measured Wellness Llc"
                                    )
                                )
                            }
                        }
                    }
                }
                return AppRequest.ListResult(dataList)
            }
        } else {
            return AppRequest.ListResult<AppointmentScheduleData>(emptyList())
        }
    }

    private fun getDoctorName(careTeam: List<Entry>?, id: String): String? {
        var doctorName: String? = ""
        careTeam?.forEach {
            val doctorData = it.resource.participant?.filter { it.member?.reference?.contains(id) == true }
            if (doctorData.isNullOrEmpty().not()) {
                 doctorName = doctorData?.firstOrNull()?.member?.display
            }
        }
        return doctorName
    }

    private fun getYearsOfPractice(entry: Entry): String {
        val presentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
        val practiceStartYear = entry.resource.extension?.find { it.url?.contains(PRACTITIONER_START_DATE) == true }?.valueInteger ?: 0
        return (presentYear - practiceStartYear).toString()
    }

    fun getMyCareTeamData(patientCareTeam: AppRequest): AppRequest {
        val dataList = mutableListOf<CareTeamData>()
        if (patientCareTeam is AppRequest.Result<*> &&  patientCareTeam.result is CareTeamResp) {
            patientCareTeam.result.let { response ->
                response.entry?.filter{ it.resource.resourceType == RESOURCE_PRACTITIONER }?.forEach {
                    val organizationData = getOrganizationData(response.entry)
                        dataList.add(
                            CareTeamData(
                                doctorName = getDoctorName(response.entry.filter { it.resource.resourceType == RESOURCE_CARE_TEAM }, it.resource.id),
                                image = it.resource.photo?.firstOrNull()?.url,
                                practitionerId = it.resource.id,
                                designation = it.resource.qualification?.firstOrNull()?.code?.text,
                                hospitalLocation = organizationData?.member?.display,
                                doctorDescription = it.resource.extension?.find { it.url?.contains(PRACTITIONER_ABOUT) == true }?.valueString,
                                gender = it.resource.gender?.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                                languages = it.resource.communication?.firstOrNull()?.text,
                                yearsOfPractice = getYearsOfPractice(it),
                                boardCertification = it.resource.qualification?.firstOrNull()?.code?.text,
                                groupAffiliations = "0 PLACE",
                                hospitalAffiliations = "0 HOSPITAL",
                                medicalSchool = it.resource.extension?.find { it.url?.contains(PRACTITIONER_EDUCATION) == true }?.valueString,
                                residency = it.resource.extension?.find { it.url?.contains(PRACTITIONER_RESIDENCY) == true }?.valueString,
                                locationAddress = getAddress(response.entry.filter { it.resource.resourceType == RESOURCE_ORGANIZATION }, organizationData?.member?.reference)
                            )
                        )
                    }
                }
            }
        return AppRequest.ListResult(dataList)
    }

    private fun getOrganizationData(entry: List<Entry>): Participant? {
        val careTeamParticipants =
            entry.firstOrNull { it.resource.resourceType == RESOURCE_CARE_TEAM }?.resource?.participant
        return careTeamParticipants?.firstOrNull {
            it.member?.reference?.contains(
                RESOURCE_ORGANIZATION
            ) == true
        }
    }

    private fun getAddress(address: List<Entry>, reference: String?): String {
        var orgAddress = ""
        reference?.let {ref ->
            val orgAddressObj = address.firstOrNull { it.fullUrl.contains(ref) }?.resource?.address?.firstOrNull()
            orgAddressObj?.let {
                orgAddress = "${it.line?.joinToString { " " }} ${it.city}, ${it.state} ${it.postalCode}"
            }
        }
        return orgAddress
    }

    fun getAppointmentsTimeSlot(timeSlotResponse: AppRequest): AppRequest {
        val dataList = mutableListOf<TimeSlotData>()
        val dateMap = linkedMapOf<DateData, List<String>>()
        val dateList = mutableListOf<String>()

        if (timeSlotResponse is AppRequest.Result<*> && timeSlotResponse.result is AppointmentResp) {
            timeSlotResponse.result.entry?.forEach {
                it.resource.start?.let { date ->
                    val localTimeDate = Instant.parse(date.substring(0, 19) + "Z").toLocalDateTime(TimeZone.currentSystemDefault())
                    val month = localTimeDate.month.name
                    val dateOfMonth = localTimeDate.dayOfMonth.toString()
                    val dayOfWeek = localTimeDate.dayOfWeek.name.subSequence(0,3).toString().uppercase()
                    val time = "${localTimeDate.time.hour % 12}:${localTimeDate.time.minute} ${if (localTimeDate.time.hour % 12 == 0) "AM" else "PM"}"
                    dateList.add(time)
                    dateMap[DateData(dayOfWeek, dateOfMonth, month, localTimeDate.year.toString())] = dateList
                }
            }
            dateMap.forEach {
                dataList.add(
                    TimeSlotData(
                        month = it.key.month,
                        year = it.key.year,
                        dayAndTimeMap = Pair(
                            it.key,
                            it.value.distinct().sortedWith { s1, s2 ->
                                when {
                                    s1.length != s2.length -> s1.length - s2.length
                                    else -> s1.compareTo(s2)
                                }
                            }.toMutableList()
                        )
                    )
                )
            }
        }
        val sortedList = dataList.toList() // Convert to a list of key-value pairs
            .sortedWith(compareBy({ it.dayAndTimeMap?.first?.month }, { it.dayAndTimeMap?.first?.date?.toInt() })) // Sort by year and then by month
        return AppRequest.ListResult(sortedList)
    }
}