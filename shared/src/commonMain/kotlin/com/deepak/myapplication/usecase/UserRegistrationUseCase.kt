package com.deepak.myapplication.usecase

import com.deepak.myapplication.repository.UserRepository
import comdeepakmyapplicationlocal.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRegistrationUseCase constructor(private val userRepository: UserRepository) {

    suspend fun registerUser(user: User): Boolean{
        return userRepository.addUser(user)
    }
}

class KMPUserRegistrationUseCaseHelper : KoinComponent{
    val userRegistrationUseCase: UserRegistrationUseCase by inject()
}