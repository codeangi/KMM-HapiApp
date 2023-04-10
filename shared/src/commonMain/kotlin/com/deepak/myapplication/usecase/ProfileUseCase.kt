package com.deepak.myapplication.usecase

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.repository.PatientRepository

class ProfileUseCase(
    private val userSettingsRepository: UserSettingsRepository,
    private val patientRepository: PatientRepository,
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
}