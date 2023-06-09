package com.deepak.myapplication.usecase

import com.deepak.myapplication.datamapper.HomeDataMapper
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.model.AccessTokenData
import com.deepak.myapplication.model.BookingResource
import com.deepak.myapplication.model.Resource
import com.deepak.myapplication.repository.PatientRepository
import com.deepak.myapplication.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeUseCase constructor(
    private val userSettingsRepository: UserSettingsRepository,
    private val patientRepository: PatientRepository,
    private val userRepository: UserRepository,
    private val dataMapper: HomeDataMapper
) {
    suspend fun getUserId() = userSettingsRepository.getUserId()
    suspend fun getPatientId() = userSettingsRepository.getPatientId()
    suspend fun getPatientDetails(): AppRequest {
        return getPatientId()?.let { patientId ->
            patientRepository.getPatientData(patientId)
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun getPatientCareTeam(): AppRequest {
        return getPatientId()?.let { patientId ->
            patientRepository.getPatientCareTeam(patientId)
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun getAccessToken(): AppRequest {
        val resp = userRepository.getToken()
        if (resp is AppRequest.Result<*> && resp.result is AccessTokenData) {
            userSettingsRepository.saveUserToken(resp.result)
        }
        return resp
    }

    suspend fun getClinicDetails(): AppRequest {
        return dataMapper.getClinicDataFromResponse()
    }

    suspend fun getDoctorsData(): AppRequest {
        return getPatientId()?.let { patientId ->
            dataMapper.getDoctorsDataFromResponse(patientRepository.getPatientCareTeam(patientId))
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun bookAppointment(resource: BookingResource): AppRequest {
        return userRepository.bookAppointment(resource)
    }

    suspend fun getPatientNearByHospitals(
        latitude: String,
        longitude: String,
        radius: Int
    ): AppRequest {
        return userRepository.getNearByHospital(
            latitude = latitude,
            longitude = longitude,
            radius = radius
        )
    }
}

class KMPHomeUseCaseHelper : KoinComponent {
    val homeUseCase: HomeUseCase by inject()
}