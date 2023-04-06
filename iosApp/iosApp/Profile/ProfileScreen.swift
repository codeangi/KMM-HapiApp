//
//  MedicationScreen.swift
//  iosApp
//
//  Created by Swasthik K S on 27/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ProfileScreen: View {
    
    @ObservedObject var viewModel: ProfileViewModel
    
    var body: some View {
        VStack(spacing: 20) {
            if let patientDetails = viewModel.patientDetails {
                let patientId = "Patient ID: \(patientDetails.id ?? "")"
                let resourceType = "Resource Type: \(patientDetails.resourceType)"
                Text(patientId)
                Text(resourceType)
            }
            Text("User Id: \(viewModel.userId)")
            Button("LOGOUT") {
                
            }
            .padding()
            .font(.title2)
            .fontWeight(.bold)
            .foregroundColor(.white)
            .background(Color.customCyan)
            .cornerRadius(5)
        }
    }
}
