//
//  LoginStatusViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 31/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

class LoginStatusViewModel: ObservableObject {
    
    private let homeUseCase = KMPHomeUseCaseHelper().homeUseCase
    @Published var patientIdAvailable: Bool = false
    @Published var userIdAvailable: Bool = false
    
    init() {
        fetchUserData()
    }
    
    func fetchUserData() {
        isPatientIdAvailable { available in
            self.patientIdAvailable = available
        }
        
        isUserIdAvailable { available in
            self.userIdAvailable = available
        }
        
    }
    
    func isPatientIdAvailable(completion: @escaping ((Bool) -> Void )) {
        homeUseCase.getPatientId { id, _ in
            completion(id != nil)
        }
    }
    
    func isUserIdAvailable(completion: @escaping ((Bool) -> Void )) {
        homeUseCase.getUserId { id, _ in
            completion(id != nil)
        }
    }
}
