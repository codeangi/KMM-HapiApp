//
//  ProfileViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 05/04/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

class ProfileViewModel: ObservableObject {
    @Published var patientDetails: PatientDataResp?
    @Published var userId: String = ""
    
    var homeUseCase = KMPHomeUseCaseHelper().homeUseCase
    
    init() {
        fetchUserData()
    }
    
    func fetchUserData() {
        homeUseCase.getPatientDetails { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestResult<PatientDataResp> else { return }
            guard let result = request.result else { return }
            self.patientDetails = result
        }
        
        homeUseCase.getUserId { userId, error in
            if let userId = userId {
                self.userId = userId
            }
        }
    }
}
