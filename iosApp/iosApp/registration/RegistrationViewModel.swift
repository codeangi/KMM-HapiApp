//
//  RegistrationViewModel.swift
//  iosApp
//
//  Created by Deepak KK on 16/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

enum RegistrationError: Error {
    case userExisting
    case unknownError
    case detailsInvalid
}

class RegistrationViewModel: ObservableObject {
    
    @Published var email:String = ""
    @Published var password:String = ""
    @Published var name:String = ""
    @Published var isLoading: Bool = false
    @Published var error: RegistrationError? = nil
    @Published var errorMessage:String = ""
    @Published var registrationStatus: Bool = false
    private let registrationUseCase = KMPUserRegistrationUseCaseHelper().userRegistrationUseCase
    
    func registration() {
        isLoading = false
        error = nil
        registrationStatus = false
        if name == "" || email == "" || password == "" {
            error = .detailsInvalid
            errorMessage = "Invalid Details"
            return
        }
        let user = User(name: name, email: email, password: password, type: 1,patient_id: "")
        
        DispatchQueue.main.async {
            self.registrationUseCase.registerUser(user: user, completionHandler: {kotlinBoolean, error in
                if error == nil {
                    print(user)
                    if let kotlinBoolean = kotlinBoolean, kotlinBoolean as! Bool {
                        self.isLoading = false
                        self.registrationStatus = true
                        print("Status of data insert:\(kotlinBoolean)")
                    }else {
                        print("Status of data insert:\(kotlinBoolean)")
                    }
                }else {
                    print("Error in insert:\((error as? NSError)?.code)")
                }
            })
        }
    }
}
