package com.deepak.myapplication.usecase

import com.deepak.myapplication.repository.UserRepository
import comdeepakmyapplicationlocal.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginUseCase constructor(private val userRepository: UserRepository) {

    suspend fun verifyUser(email:String, password:String): User?{
        return userRepository.verifyUser(email,password)
    }
}

class  KMPLoginUseCaseHelper: KoinComponent {
    val loginUseCase : LoginUseCase by inject()
}