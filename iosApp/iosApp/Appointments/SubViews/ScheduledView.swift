//
//  ScheduledView.swift
//  iosApp
//
//  Created by Swasthik K S on 31/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ScheduledView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .center) {
            LottieView(lottieFile: "successful_animation")
            Spacer()
            Button("VIEW DETAILS") {
                
            }
            .padding()
            .frame(width: viewModel.screenWidth - 60, height: 50)
            .font(.headline)
            .fontWeight(.bold)
            .foregroundColor(Color.white)
            .background(Color.customCyan)
            .cornerRadius(10)
            Button("DONE") {
                viewModel.navigateToRoot()
            }
            .padding()
            .frame(width: viewModel.screenWidth - 60, height: 50)
            .font(.headline)
            .fontWeight(.bold)
            .foregroundColor(Color.customCyan)
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.customCyan, lineWidth: 2)
            )
        }
        .navigationBarHidden(true)
    }
}

struct ScheduledView_Previews: PreviewProvider {
    static var previews: some View {
        ScheduledView().environmentObject(AppointmentViewModel())
    }
}
