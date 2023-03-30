//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Deepak KK on 20/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
class LoginViewModel : ObservableObject {
    
    @Published var email:String = ""
    @Published var password:String = ""
    @Published var loginSuccess:Bool = false
    @Published var error = true
    @Published var errorMessage:String = ""
    private let loginUseCase = KMPLoginUseCaseHelper().loginUseCase
    
    func login() {
        if email == "" || password == "" {
            self.error = true
            self.errorMessage = "Invalid details"
            return
        }
        loginUseCase.loginUser(email: email, password: password, completionHandler:{user, error in
            if user != nil {
                self.loginSuccess = true
            } else {
                self.error = true
                self.errorMessage = "Invalid details"
            }
        })
    }
}
