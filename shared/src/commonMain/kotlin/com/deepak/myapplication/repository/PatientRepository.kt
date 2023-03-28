package com.deepak.myapplication.repository

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.infra.RemoteRoutes
import com.deepak.myapplication.model.PatientDataResp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface PatientRepository {
    suspend fun getPatientData(patientId:String): AppRequest
}
class PatientRepositoryImpl constructor(private val httpClient: HttpClient): PatientRepository {

    override suspend fun getPatientData(patientId: String): AppRequest {
       return try {
            val response =httpClient.get(urlString = RemoteRoutes.GET_PATIENT_DETAILS(patientId))
            val data = response.body<PatientDataResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }
}