package com.deepak.myapplication.usecase

import com.deepak.myapplication.repository.PractitionerRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PractitionerUseCase(private val practitionerRepository: PractitionerRepository) {

    suspend fun getPractitionerDetails(practitionerId: String) =
        practitionerRepository.getPractitionerDetails(practitionerId = practitionerId)
}

class KMPPractitionerUseCase : KoinComponent {
    val practitionerUseCase: PractitionerUseCase by inject()
}