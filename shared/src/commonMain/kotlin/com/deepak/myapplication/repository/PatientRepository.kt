package com.deepak.myapplication.repository

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.infra.RemoteRoutes
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.model.AppointmentResp
import com.deepak.myapplication.model.CareTeamResp
import com.deepak.myapplication.model.PatientDataResp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface PatientRepository {
    suspend fun getPatientData(patientId: String): AppRequest
    suspend fun getPatientCareTeam(patientId: String): AppRequest

    suspend fun getPatientAppointments(
        patientId: String,
        startDate: String,
        endDate: String,
        count: Int
    ): AppRequest
}

class PatientRepositoryImpl constructor(
    private val httpClient: HttpClient,
    private val userSettingsRepository: UserSettingsRepository
) : PatientRepository {

    override suspend fun getPatientData(patientId: String): AppRequest {
        return try {
            val response = httpClient.get(urlString = RemoteRoutes.GET_PATIENT_DETAILS(patientId))
            val data = response.body<PatientDataResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }

    override suspend fun getPatientCareTeam(patientId: String): AppRequest {
        return try {
            val response = httpClient.get(RemoteRoutes.GET_PATIENT_CARE_TEAM) {

                url {
                    parameters.append(RemoteRoutes.Parameters.Participant, patientId)
                    parameters.append("_include" ,"CareTeam:participant")
                }
            }
            val data = response.body<CareTeamResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }

    }

    override suspend fun getPatientAppointments(
        patientId: String,
        startDate: String,
        endDate: String,
        count: Int,
    ): AppRequest {

        return try {
            val responseData = httpClient.get(RemoteRoutes.APPOINTMENT) {
                val token = userSettingsRepository.getAccessToken() ?: ""
                println("User token:$token")
                bearerAuth(token)
                url {
                    parameters.append("actor:Patient", patientId)
                    parameters.append("_count", count.toString())
                    parameters.append("status:not", "cancelled")
                    if (endDate.isNotEmpty()) {
                        parameters.append("date", "lt$endDate")
                    }
                    if (startDate.isNotEmpty()) {
                        parameters.append("date", "gt$startDate")
                    }
                    parameters.append("_include", "Appointment:practitioner")
                    parameters.append("_include", "Appointment:reason-reference")
                }
            }
            val data = responseData.body<AppointmentResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }

}