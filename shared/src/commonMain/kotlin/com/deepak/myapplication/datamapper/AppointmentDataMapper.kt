package com.deepak.myapplication.datamapper

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.*

class AppointmentDataMapper {
    val aboutText = "Dr. Parker’s research involves nutritional support of the premature infant with an emphasis on breastfeeding infants in the neonatal intensive care unit (NICU). She has been funded by the National Institute of Nursing Research to study (1) the risks and benefits of routine gastric residual aspiration and evaluation in very premature infants and (2) the optimal timing of initiation of milk expression following the delivery of a very premature infant. She recently received a 2018 Research Opportunity grant to study strategies to increase lactation success in mothers of extremely premature infants. She received a UF Term Professorship award in 2017." +
            "SERVICE" +
            "Dr. Parker is board certified as a neonatal nurse practitioner by the National Certification Corporation. She has an active practice as a neonatal nurse practitioner in the NICU at UF Health."


    fun getPatientAppointmentSchedules() : AppRequest {
        val data = listOf(
            AppointmentScheduleData(
                "Headache",
                "Tuesday, Mar 14 at 8.30 PM",
                "Dr. Leslie Crona, RN",
                "Measured Wellness Llc"
            ),
            AppointmentScheduleData(
                "Viral Fever",
                "Tuesday, Mar 12 at 8.30 PM",
                "Dr. Leslie Crona, RN",
                "Measured Wellness Llc"
            ),
            AppointmentScheduleData(
                "Headache",
                "Tuesday, Mar 11 at 8.30 PM",
                "Dr. Betsey Kemmer, DOS",
                "Measured Wellness Llc"
            ),
            AppointmentScheduleData(
                "Headache",
                "Tuesday, Mar 21 at 8.30 PM",
                "Dr. Leslie Crona, RN",
                "Measured Wellness Llc"
            ),
            AppointmentScheduleData(
                "Headache",
                "Tuesday, Mar 24 at 8.30 PM",
                "Dr. Betsey Kemmer, DOS",
                "Measured Wellness Llc"
            )
        )
        return AppRequest.ListResult(data)
    }

    fun getMyCareTeamData(patientCareTeam: AppRequest): AppRequest {
//        val dataList = mutableListOf<CareTeamData>()
//        if (patientCareTeam is AppRequest.Result<*> &&  patientCareTeam.result is CareTeamResp) {
//            patientCareTeam.result.let {
//                it.entry?.forEach {
//                    CareTeamData(
//                        doctorName = "Dr. Leslie Crona, RN",
//                        designation = "Neuropathology",
//                        hospitalLocation = "Measured Wellness Llc",
//                        doctorDescription = aboutText,
//                        gender = "Female",
//                        languages = "Spanish",
//                        yearsOfPractice = "3",
//                        boardCertification = "Registered Nurse",
//                        groupAffiliations = "0 PLACE",
//                        hospitalAffiliations = "0 HOSPITAL",
//                        medicalSchool = "Boston University",
//                        residency = "CHA Cambridge Hospital",
//                        locationAddress = "47 Seaverns Ave #5 Brookline, MA 024463806"
//                    )
//                }
//            }
//
//        }

        val data = listOf(
            CareTeamData(
                doctorName = "Dr. Leslie Crona, RN",
                designation = "Neuropathology",
                hospitalLocation = "Measured Wellness Llc",
                doctorDescription = aboutText,
                gender = "Female",
                languages = "Spanish",
                yearsOfPractice = "3",
                boardCertification = "Registered Nurse",
                groupAffiliations = "0 PLACE",
                hospitalAffiliations = "0 HOSPITAL",
                medicalSchool = "Boston University",
                residency = "CHA Cambridge Hospital",
                locationAddress = "47 Seaverns Ave #5 Brookline, MA 024463806"
            ),
            CareTeamData(
                doctorName = "Dr. Betsey Kemmer, DOS",
                designation = "Homeopathy",
                hospitalLocation = "Measured Wellness Llc",
                doctorDescription = aboutText,
                gender = "Female",
                languages = "English",
                yearsOfPractice = "4",
                boardCertification = "Doctor of Science",
                groupAffiliations = "0 PLACE",
                hospitalAffiliations = "0 HOSPITAL",
                medicalSchool = "Boston University",
                residency = "CHA Cambridge Hospital",
                locationAddress = "47 Seaverns Ave #5 Brookline, MA 024463806"
            )
        )
        return AppRequest.ListResult(data)
    }

    fun getAppointmentsTimeSlot(): AppRequest {
        val data = listOf(
            TimeSlotData(
                month = "April",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("14", listOf("6.30 PM", "10.30 PM"))
                )
            ),
            TimeSlotData(
                month = "April",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("16", listOf("6.30 PM", "10.30 PM"))
                )

            ),
            TimeSlotData(
                month = "April",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("21", listOf("6.30 PM","8.30 PM", "10.30 PM"))
                )

            ),
            TimeSlotData(
                month = "May",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("08", listOf("6.30 PM", "10.30 PM", "12.30 PM",))
                )
            ),
            TimeSlotData(
                month = "May",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("14", listOf("6.30 PM"))
                )
            ),
            TimeSlotData(
                month = "May",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("21", listOf("6.30 PM", "10.30 PM", "12.30 PM","18.30 PM"))
                )
            ),

            TimeSlotData(
                month = "May",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("22", listOf("6.30 PM", "10.30 PM"))
                )
            ),

            TimeSlotData(
                month = "June",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("01", listOf("6.30 PM", "10.30 PM"))
                )
            ),
            TimeSlotData(
                month = "June",
                year = "2023",
                dayAndTimeMap = hashMapOf(
                    Pair("04", listOf("6.30 PM", "10.30 PM"))
                )
            )
        )
        return AppRequest.ListResult(data)
    }
}