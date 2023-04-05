package com.deepak.myapplication.datamapper

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.model.*

class HomeDataMapper() {

    fun getClinicDataFromResponse(): AppRequest {
        val data = listOf(
            ClinicData("Franciscan Hospital for Children", "30 Warren St Boston, MA 021353602"),
            ClinicData("Cambridge Hospital", "33 Tower St, Somerville, MA 02143, United States")
        )
        return AppRequest.ListResult(data)
    }

    fun getDoctorsDataFromResponse(patientCareTeam: AppRequest): AppRequest {
        val dataList = mutableListOf<DoctorData>()
        if (patientCareTeam is AppRequest.Result<*> &&  patientCareTeam.result is CareTeamResp) {
            patientCareTeam.result.let { response ->
                response.entry?.filter{ it.resource.resourceType == "Practitioner" }?.forEach {
                    dataList.add(
                        DoctorData(
                            doctorName = getDoctorName(response.entry.filter { it.resource.resourceType == "CareTeam" }, it.resource.id),
                            designation = it.resource.qualification?.firstOrNull()?.code?.text,
                            imageUrl = it.resource.photo?.firstOrNull()?.url
                        )
                    )
                }
            }
        }
        return AppRequest.ListResult(dataList)
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

}