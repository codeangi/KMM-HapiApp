package com.deepak.myapplication.repository

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.infra.RemoteRoutes
import com.deepak.myapplication.model.PractitionerDetailResp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface PractitionerRepository {
    suspend fun getPractitionerDetails(practitionerId: String): AppRequest
}

class PractitionerRepositoryImpl(private val httpClient: HttpClient) : PractitionerRepository {
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
}