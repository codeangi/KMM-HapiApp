//
//  LocationView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LocationsView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            ProgressBar(currentProgress: AppointmentScreens.location.progressCount)
            VStack(alignment: .leading) {
                Text("Where would you like to be seen?")
                    .font(.title)
                    .fontWeight(.bold)
                ScrollView {
                    LocationCard(careDetails: viewModel.getCareTeamData()).environmentObject(viewModel)
                }
                Spacer()
            }
        }
        .navigationTitle("Schedule Appointment")
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
        .navigationBarItems(trailing: navBarCancelButton(viewModel: viewModel))
        .padding()
    }
}

struct LocationCard: View {
    
    var careDetails: CareTeamData
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack {
            HStack(alignment: .top) {
                Image("clinic")
                    .resizable()
                    .frame(width: 60, height: 60)
                    .cornerRadius(10)
                VStack(alignment: .leading, spacing: 10) {
                    Text(careDetails.hospitalLocation ?? "")
                    HStack(alignment: .top) {
                        Image(systemName: "mappin.and.ellipse")
                        Text(careDetails.locationAddress ?? "")
                            .font(.subheadline)
                        .foregroundColor(.black.opacity(0.6))
                    }
                    HStack(alignment: .top) {
                        Image(systemName: "clock")
                        Text("Attending hours:\n8 AM - 2 PM - Mon, Tue, Wed, Thu, Fri")
                            .font(.subheadline)
                        .foregroundColor(.black.opacity(0.6))
                    }
                }
                .padding(.leading)
                Spacer()
            }
        }
        .padding()
        .cornerRadius(20)
        .background(Color.lightGrey.opacity(0.1))
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Color.lightGrey.opacity(0.9), lineWidth: 1)
        )
        .onTapGesture {
            viewModel.setDateTime()
            viewModel.selectedAppointmentData.appointmentLocationAddress = (careDetails.hospitalLocation ?? "") + (careDetails.locationAddress ?? "")
            viewModel.appendScreen(screenType: .dateTime)
        }
    }
}

struct LocationsView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            LocationsView().environmentObject(AppointmentViewModel())
        }
    }
}
