package com.deepak.myapplication.usecase

import com.deepak.myapplication.datamapper.AppointmentDataMapper
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.repository.PatientRepository

class AppointmentUseCase  constructor(
    private val userSettingsRepository: UserSettingsRepository,
    private val patientRepository: PatientRepository,
    private val appointmentDataMapper: AppointmentDataMapper
) {
    fun getPatientAppointmentSchedules(): AppRequest {
        return appointmentDataMapper.getPatientAppointmentSchedules()
    }

    suspend fun getPatientId() = userSettingsRepository.getPatientId()

    suspend fun getMyCareTeamData(): AppRequest {
        return getPatientId()?.let { patientId ->
            appointmentDataMapper.getMyCareTeamData(patientRepository.getPatientCareTeam(patientId))
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }
}