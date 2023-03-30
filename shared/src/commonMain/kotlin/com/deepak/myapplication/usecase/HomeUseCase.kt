package com.deepak.myapplication.usecase

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.repository.PatientRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeUseCase constructor(
    private val userSettingsRepository: UserSettingsRepository,
    private val patientRepository: PatientRepository
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

    suspend fun getPatientCareTeam(): AppRequest{
        return getPatientId()?.let { patientId ->
            patientRepository.getPatientCareTeam(patientId)
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }
}

class KMPHomeUseCaseHelper : KoinComponent {
    val homeUseCase: HomeUseCase by inject()
}