package com.deepak.myapplication.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.deepak.myapplication.AppConstant
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.repository.UserRepository
import comdeepakmyapplicationlocal.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRegistrationUseCase constructor(private val userRepository: UserRepository, private val userSettingsRepository: UserSettingsRepository) {

    suspend fun registerUser(user: User): Boolean{
        val selectedPatientId = AppConstant.AVAILABLE_PATIENT_ID.shuffled().first()
        val status=  userRepository.addUser(user.copy(patient_id = selectedPatientId))
        if(status){
            userSettingsRepository.saveUserId(userId = user.email)
            userSettingsRepository.savePatientId(patientId = user.patient_id)
        }
        return true
    }
}

class KMPUserRegistrationUseCaseHelper : KoinComponent{
    val userRegistrationUseCase: UserRegistrationUseCase by inject()
}