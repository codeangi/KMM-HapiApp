package com.deepak.myapplication.repository

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.infra.RemoteRoutes
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

interface PractitionerRepository {
    suspend fun getPractitionerDetails(practitionerId: String): AppRequest

    suspend fun getAppointment(
        practitionerId: String,
        patientId: String,
        starDate: String,
        endDate: String
    ): AppRequest
}

class PractitionerRepositoryImpl(
    private val httpClient: HttpClient,
    private val userSettingsRepository: UserSettingsRepository
) : PractitionerRepository {
    override suspend fun getPractitionerDetails(practitionerId: String): AppRequest {
        return try {
            val resp =
                httpClient.get(RemoteRoutes.GET_PRACTITIONER_DETAILS(practitionerId = practitionerId))
            val data = resp.body<PractitionerDetailResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }

    override suspend fun getAppointment(
        practitionerId: String,
        patientId: String,
        startDate: String,
        endDate: String
    ): AppRequest {
        val startDateParam = Parameter(name = "start", valueDateTime = startDate)
        val endDateParam = Parameter(name = "end", valueDateTime = endDate)
        val practitioner = Parameter(
            name = "practitioner-reference",
            valueReference = ValueReference(reference = "Practitioner/$practitionerId")
        )
        val patient = Parameter(
            name = "patient-reference",
            valueReference = ValueReference(reference = "Patient/$patientId")
        )
        val parameter = mutableListOf<Parameter>().apply {
            add(startDateParam)
            add(endDateParam)
            add(practitioner)
            add(patient)
            add(practitioner)
        }
        return try {
            val appointmentReq = AppointmentReq(resourceType = "Parameters", parameter = parameter)
            val resp = httpClient.post(RemoteRoutes.SLOT) {
                val token = userSettingsRepository.getAccessToken() ?: ""
                println("User token:$token")
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(appointmentReq)
            }
            val data = resp.body<AppointmentResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }
}