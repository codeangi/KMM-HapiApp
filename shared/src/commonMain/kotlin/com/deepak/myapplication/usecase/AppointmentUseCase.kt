package com.deepak.myapplication.usecase

import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.repository.PatientRepository
import com.deepak.myapplication.repository.UserRepository
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppointmentUseCase(
    private val userSettingsRepository: UserSettingsRepository,
    private val patientRepository: PatientRepository,
    private val userRepository: UserRepository
) {

    private suspend fun getPatientId() = userSettingsRepository.getPatientId()
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

    suspend fun getPatientFutureAppointments(): AppRequest {
        val startDate = Clock.System.now()
        return getPatientId()?.let { patientId ->
            patientRepository.getPatientAppointments(
                patientId = patientId,
                startDate = startDate.toString(),
                endDate = "",
                count = 100
            )
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun getPatientPastAppointments(): AppRequest {
        val endDate = Clock.System.now()
        return getPatientId()?.let { patientId ->
            patientRepository.getPatientAppointments(
                patientId = patientId,
                startDate = "",
                endDate = endDate.toString(),
                count = 100
            )
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }
}

class KMPAppointmentUseCaseHelper : KoinComponent {
    val appointmentUseCase: AppointmentUseCase by inject()
}
