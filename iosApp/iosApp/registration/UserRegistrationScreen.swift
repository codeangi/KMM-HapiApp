//
//  LoginScene.swift
//  iosApp
//
//  Created by Deepak KK on 16/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
struct UserRegistrationScreen: View {
    
    @ObservedObject var viewModel: RegistrationViewModel
    
    var body: some View {
        VStack {
            Image(systemName: "lock.shield.fill")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 100, height: 100)
                .padding()
            
            Text("Registration")
                .font(.largeTitle)
                .bold()
                .padding()
            
            TextField("Name", text: $viewModel.name)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
                .padding(.horizontal, 20)
            
            TextField("Email", text: $viewModel.email)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
                .padding(.horizontal, 20)
            
            
            SecureField("Password", text: $viewModel.password)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
                .padding(.horizontal,20)
            
            Button(action: {
                //Perform signup action
                viewModel.registration()
            }, label: {
                Text("Submit")
                    .font(.headline)
                    .foregroundColor(.white)
                    .padding().frame(width: 220, height: 60)
                    .background(Color.blue)
                    .cornerRadius(15)
            }).fullScreenCover(isPresented: $viewModel.registrationStatus) {
                TabBarView()
            }
            if viewModel.error != nil {
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

struct LoginScene_Previews: PreviewProvider {
    static var previews: some View {
        UserRegistrationScreen(viewModel: RegistrationViewModel())
    }
}
