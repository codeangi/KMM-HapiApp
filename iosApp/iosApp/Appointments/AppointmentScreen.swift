//
//  AppointmentScreen.swift
//  iosApp
//
//  Created by Swasthik K S on 27/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct AppointmentScreen: View {
    
    @ObservedObject var viewModel: AppointmentViewModel
    
    var body: some View {
        NavigationStack(path: $viewModel.path) {
            ScrollView {
                VStack(alignment: .leading) {
                    Text("Today")
                        .font(.title)
                        .fontWeight(.bold)
                    ForEach(viewModel.current, id: \.self) { appointment in
                        VStack(alignment: .leading) {
                            AppointmentCell(appointment: appointment, isCurrent: true)
                        }
                        .padding(.bottom, 20)
                    }
                    
                    Text("Upcoming")
                        .font(.title)
                        .fontWeight(.bold)
                    ForEach(viewModel.upcoming, id: \.self) { appointment in
                        VStack(alignment: .leading) {
                            AppointmentCell(appointment: appointment, isCurrent: false)
                        }
                        .padding(.bottom, 20)
                    }
                    
                    Text("Past")
                        .font(.title)
                        .fontWeight(.bold)
                    ForEach(viewModel.past, id: \.self) { appointment in
                        VStack(alignment: .leading) {
                            AppointmentCell(appointment: appointment, isCurrent: false)
                        }
                        .padding(.bottom, 20)
                    }
                }
                .padding(20)
                .navigationTitle("Appointments")
                .toolbar {
                    Button(action: { viewModel.appendScreen(screenType: .careTeam) }, label: {
                        Image(systemName: "plus")
                            .resizable()
                            .frame(width: 15, height: 15)
                        Text("ADD")
                            .font(.body)
                            .fontWeight(.bold)
                            .padding(.leading, 5)
                    })
                    .padding(5)
                    .padding(.horizontal, 5)
                    .foregroundColor(.white)
                    .background(Color.customCyan)
                    .cornerRadius(30)
                }
                .navigationDestination(for: AppointmentScreens.self) { screen in
                    switch screen {
                    case .careTeam:
                        CareTeamListView()
                            .environmentObject(viewModel)
                    case .careDetail:
                        CareDetailsView()
                            .environmentObject(viewModel)
                    case .reason:
                        ReasonView()
                            .environmentObject(viewModel)
                    case .type:
                        TypeView()
                            .environmentObject(viewModel)
                    case .location:
                        LocationsView()
                            .environmentObject(viewModel)
                    case .dateTime:
                        DateTimeView()
                            .environmentObject(viewModel)
                    case .review:
                        ReviewView()
                            .environmentObject(viewModel)
                    case .scheduled:
                        ScheduledView()
                            .environmentObject(viewModel)
                    default:
                        EmptyView(viewModel: EmptyViewModel(title: "Empty"))
                    }
                }
            }
        }
    }
}

struct AppointmentCell: View {
    
    @State var appointment: AppointmentModel
    @State var isCurrent: Bool
    
    var body: some View {
        VStack(alignment: .leading) {
            if isCurrent {
                Image("clinic")
                    .resizable()
                    .frame(height: 200)
            }
            VStack(alignment: .leading, spacing: 15) {
                HStack {
                    Text(appointment.type)
                        .font(.title3)
                        .fontWeight(.bold)
                    Spacer()
                    Image(systemName: "chevron.right")
                        .padding(.trailing, 10)
                        .foregroundColor(.black.opacity(0.5))
                }
                HStack(spacing: 10) {
                    Image(systemName: "wallet.pass")
                        .foregroundColor(Color.customCyan)
                    Text(appointment.date)
                        .foregroundColor(.black.opacity(0.6))
                }
                HStack(spacing: 10) {
                    Image(systemName: "person")
                        .foregroundColor(Color.customCyan)
                    Text(appointment.doctor)
                        .foregroundColor(.black.opacity(0.6))
                }
                HStack(spacing: 10) {
                    Image(systemName: "mappin.and.ellipse")
                        .foregroundColor(Color.customCyan)
                    Text(appointment.careName)
                        .foregroundColor(.black.opacity(0.6))
                }
            }
            .padding(20)
        }
        .background(Color.lightGrey.opacity(0.1))
        .cornerRadius(20)
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Color.lightGrey.opacity(0.9), lineWidth: 1)
        )
    }
}

struct AppointmentScreen_Previews: PreviewProvider {
    static var previews: some View {
        AppointmentScreen(viewModel: AppointmentViewModel())
    }
}
