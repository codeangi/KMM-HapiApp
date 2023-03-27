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
    
    @Published var userName:String = ""
    @Published var password:String = ""
    @Published var loginSuccess:Bool = false
    @Published var error = true
    @Published var errorMessage:String = ""
    private let loginUseCase = KMPLoginUseCaseHelper().loginUseCase
    func login() {	
        loginUseCase.verifyUser(email: userName, password: password, completionHandler:{user, error in
            if error == nil {
                self.loginSuccess = true
            }else {
                self.error = true
                self.errorMessage = "Invalid details"
            }
        })
    }
    
}
