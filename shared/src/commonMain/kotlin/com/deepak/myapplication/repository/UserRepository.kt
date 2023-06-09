package com.deepak.myapplication.repository

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.infra.RemoteRoutes
import com.deepak.myapplication.local.DataBase
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.model.AccessTokenData
import com.deepak.myapplication.model.BookingResource
import com.deepak.myapplication.model.LocationDataResp
import com.deepak.myapplication.model.Resource
import comdeepakmyapplicationlocal.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface UserRepository {
    suspend fun addUser(user: User): Boolean
    suspend fun verifyUser(email: String, password: String): User?
    suspend fun getTheUser(email: String): User?
    suspend fun getToken(): AppRequest

    suspend fun bookAppointment(appointmentSlotReq: BookingResource): AppRequest

    suspend fun getNearByHospital(latitude: String, longitude: String, radius: Int): AppRequest
}

class UserRepositoryImpl constructor(
    private val httpClient: HttpClient, private val userSettingsRepository: UserSettingsRepository
) : UserRepository, KoinComponent {

    private val database: DataBase by inject()

    override suspend fun addUser(user: User): Boolean {
        return database.insertUser(user)
    }

    override suspend fun verifyUser(email: String, password: String): User? {
        return database.validateUser(email = email, password = password)
    }

    override suspend fun getTheUser(email: String): User? {
        return database.getUser(email = email)
    }

    override suspend fun getToken(): AppRequest {
        return try {
            val response = httpClient.post(RemoteRoutes.ACCESS_TOKEN) {
                basicAuth("a7b78fe2-0f34-41e1-acc7-8c0d42358892", "password")
                setBody(FormDataContent(Parameters.build {
                    append("grant_type", "client_credentials")
                    append("tenant_id", "0bfc3213-dec7-4a94-b8b2-b0594babb972")
                }))
            }
            val data = response.body<AccessTokenData>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }

    override suspend fun bookAppointment(appointmentSlotReq: BookingResource): AppRequest {
        return try {
            val response = httpClient.post(RemoteRoutes.APPOINTMENT) {
                val token = userSettingsRepository.getAccessToken() ?: ""
                bearerAuth(token)
                setBody(appointmentSlotReq)
                contentType(ContentType.Application.Json)
            }
            val data = response.body<Resource>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }

    override suspend fun getNearByHospital(
        latitude: String, longitude: String, radius: Int
    ): AppRequest {
        return try {
            val response = httpClient.get {
                url(RemoteRoutes.NEARBY_LOCATIONS)
                parameter(RemoteRoutes.Parameters.NEAR, "$latitude|$longitude|${radius}|mi")
                val token = userSettingsRepository.getAccessToken() ?: ""
                bearerAuth(token)

            }
            val data = response.body<LocationDataResp>()
            AppRequest.Result(data)
        } catch (e: Exception) {
            AppRequest.Error(e)
        }
    }
}