package com.deepak.myapplication.usecase

import com.deepak.myapplication.local.UserSettingsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeUseCase constructor(private val userSettingsRepository: UserSettingsRepository) {
    suspend fun getUserId() = userSettingsRepository.getUserId()
    suspend fun getPatientId() = userSettingsRepository.getPatientId()
}

class KMPHomeUseCaseHelper: KoinComponent{
    val homeUseCase : HomeUseCase by inject()
}