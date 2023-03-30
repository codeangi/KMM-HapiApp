//
//  LoginScreen.swift
//  iosApp
//
//  Created by Deepak KK on 17/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
struct LoginScreen: View {
    @ObservedObject var viewModel : LoginViewModel
    var body: some View {
        VStack(){
            Image(systemName: "lock.shield.fill")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 100, height: 100)
                .padding()
            
            Text("Login").font(.largeTitle)
                .bold()
                .padding()
            
            TextField("Email", text: $viewModel.email)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
                .padding(.horizontal, 20)
            
            SecureField("Password", text: $viewModel.password)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
                .padding(.horizontal, 20)
            
            Button(action: {
                viewModel.login()
            }, label: {
                Text("Sign In")
                    .padding()
                    .frame(width: 220, height: 60)
                    .font(.headline)
                    .foregroundColor(Color.white)
                    .background(Color.blue)
                    .cornerRadius(15)
            }).fullScreenCover(isPresented: $viewModel.loginSuccess) {
                TabBarView()
            }
            
            NavigationLink(destination: UserRegistrationScreen(viewModel: RegistrationViewModel()), label: {
                Text("New User?")
                    .padding()
                    .font(.headline)
                    .frame(width: 220, height: 60)
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(16)
            })
            
            if viewModel.error {
                Text(viewModel.errorMessage)
                    .bold()
                    .background(Color.white.opacity(0.2))
                    .padding()
                    .font(.subheadline)
                    .foregroundColor(Color.red)
            }
        }
    }
}
