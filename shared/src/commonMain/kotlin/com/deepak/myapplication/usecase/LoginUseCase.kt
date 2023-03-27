package com.deepak.myapplication.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.deepak.myapplication.local.UserSettingsRepository
import com.deepak.myapplication.repository.UserRepository
import comdeepakmyapplicationlocal.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginUseCase constructor(private val userRepository: UserRepository, private val userStore: UserSettingsRepository) {

    suspend fun loginUser(email:String, password:String): User?{
        return userRepository.verifyUser(email,password)?.apply {
            userStore.saveUserId(this.email)
            userStore.savePatientId(this.patient_id)
        }
    }
}

class  KMPLoginUseCaseHelper: KoinComponent {
    val loginUseCase : LoginUseCase by inject()
}