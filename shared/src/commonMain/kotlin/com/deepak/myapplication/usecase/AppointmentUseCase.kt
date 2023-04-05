package com.deepak.myapplication.usecase

import com.deepak.myapplication.datamapper.AppointmentDataMapper
import com.deepak.myapplication.infra.AppRequest
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.model.BookingResource
import com.deepak.myapplication.repository.PatientRepository
import com.deepak.myapplication.repository.PractitionerRepository
import com.deepak.myapplication.repository.UserRepository
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration

class AppointmentUseCase(
    private val userSettingsRepository: UserSettingsRepository,
    private val patientRepository: PatientRepository,
    private val practitionerRepository: PractitionerRepository,
    private val userRepository: UserRepository,
    private val appointmentDataMapper: AppointmentDataMapper
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
            appointmentDataMapper.getPatientAppointmentSchedules(
                patientRepository.getPatientAppointments(
                    patientId = patientId,
                    startDate = startDate.toString(),
                    endDate = "",
                    count = 100
                )
            )
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun getPatientTodaysAppointments(): AppRequest {
        val startDate = Clock.System.now()

        return getPatientId()?.let { patientId ->
            appointmentDataMapper.getPatientAppointmentSchedules(
                patientRepository.getPatientAppointments(
                    patientId = patientId,
                    startDate = startDate.toString(),
                    endDate = "",
                    count = 5
                ),
                isToday = true
            )
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun getPatientPastAppointments(): AppRequest {
        val endDate = Clock.System.now()
        return getPatientId()?.let { patientId ->
            appointmentDataMapper.getPatientAppointmentSchedules(
                patientRepository.getPatientAppointments(
                    patientId = patientId,
                    startDate = "",
                    endDate = endDate.toString(),
                    count = 5
                )
            )
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun getAppointmentSlots(practitionerId: String): AppRequest {
        val startDate = Clock.System.now()
        val endDate = Clock.System.now().plus(Duration.parse("14d"))
        return getPatientId()?.let { patientId ->
            appointmentDataMapper.getAppointmentsTimeSlot(
                practitionerRepository.getAppointment(
                    practitionerId = practitionerId,
                    patientId = patientId,
                    starDate = startDate.toString(),
                    endDate = endDate.toString()
                )
            )
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }

    }

    suspend fun getMyCareTeamData(): AppRequest {
        return getPatientId()?.let { patientId ->
            appointmentDataMapper.getMyCareTeamData(patientRepository.getPatientCareTeam(patientId))
        } ?: kotlin.run {
            AppRequest.Error(Exception("Patient should not be null"))
        }
    }

    suspend fun bookAppointment(resource: BookingResource): AppRequest {
        return userRepository.bookAppointment(resource)
    }
}

class KMPAppointmentUseCaseHelper : KoinComponent {
    val appointmentUseCase: AppointmentUseCase by inject()
}
