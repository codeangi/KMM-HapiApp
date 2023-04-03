//
//  HapiHomeScreen.swift
//  iosApp
//
//  Created by Deepak KK on 21/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HapiHomeScreen: View {
    
    @ObservedObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        NavigationStack(path: $viewModel.path) {
            VStack(alignment: .leading) {
                NavBar()
                    .environmentObject(viewModel)
                Divider()
                ScrollView(.vertical, showsIndicators: false) {
                    VStack(alignment: .leading) {
                        Header()
                        OptionCells()
                            .environmentObject(viewModel)
                        BookNowCard()
                            .environmentObject(viewModel)
                        Text("My Doctors")
                            .foregroundColor(Color.gray)
                            .font(.title2)
                            .fontWeight(.bold)
                            .padding(.top, 20)
                        DoctorsCells()
                            .environmentObject(viewModel)
                        Text("Clinics")
                            .foregroundColor(Color.gray)
                            .font(.title2)
                            .fontWeight(.bold)
                            .padding(.top, 20)
                        ClinicCells()
                            .environmentObject(viewModel)
                        Text("Medical Records")
                            .foregroundColor(Color.gray)
                            .font(.title2)
                            .fontWeight(.bold)
                            .padding(.top, 20)
                        MedicalRecordsCells()
                            .environmentObject(viewModel)
                    }
                }
                Spacer()
            }
            .padding()
            .navigationDestination(for: String.self) { title in
                EmptyView(viewModel: EmptyViewModel(title: title))
            }
        }
    }
}

struct NavBar: View {
    
    @EnvironmentObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        HStack {
            Image(systemName: "house")
                .resizable()
                .foregroundColor(.customCyan)
                .frame(width: 25, height: 25)
            Text("Sutter Health")
                .foregroundColor(.customCyan)
                .fontWeight(.bold)
                .font(.title)
            Spacer()
            Image(systemName: "phone.circle.fill")
                .resizable()
                .foregroundColor(.customCyan)
                .frame(width: 25, height: 25)
                .onTapGesture {
                    viewModel.navigateToEmptyView(screenTitle: "Contact")
                }
        }
    }
}

struct Header: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 5) {
            Text("Hi, Rowena")
                .font(.title)
                .fontWeight(.semibold)
            Text("Monday, March 13")
                .font(.subheadline)
        }
        .padding(.top, 10)
    }
}

struct OptionCells: View {
    
    @EnvironmentObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 10) {
                ForEach(viewModel.options, id: \.self) { option in
                    Text(option)
                        .foregroundColor(.customCyan)
                        .fontWeight(.bold)
                        .onTapGesture {
                            viewModel.navigateToEmptyView(screenTitle: option)
                        }
                }
                .padding(.horizontal, 20)
                .padding(.vertical, 10)
                .overlay(
                    Capsule()
                        .stroke(Color.customCyan, lineWidth: 2)
                )
            }
            .padding(5)
        }
        .padding(.top, 20)
    }
}

struct BookNowCard: View {
    
    @EnvironmentObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        HStack {
            Image(systemName: "bolt.circle.fill")
                .resizable()
                .foregroundColor(.white)
                .frame(width: 50, height: 50)
            VStack(alignment: .leading, spacing: 5) {
                Text("Book now")
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                Text("Get the next available appointment, now.")
                    .foregroundColor(.white)
            }
            .padding(.leading, 10)
            Spacer()
            Image(systemName: "arrow.right")
                .foregroundColor(.white)
        }
        .onTapGesture(perform: {
            viewModel.navigateToEmptyView(screenTitle: "Book now")
        })
        .padding(15)
        .background(Color.customBlue)
        .cornerRadius(10)
    }
}

struct DoctorsCells: View {
    
    @EnvironmentObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 10) {
                ForEach(viewModel.doctors, id: \.self) { doctor in
                    VStack(alignment: .leading) {
                        VStack {
                            Image(systemName: "person.fill")
                                .resizable()
                                .frame(width: 50, height: 50)
                        }
                        .frame(width: 150, height: 170)
                        .background(Color.customCyan)
                        .cornerRadius(20)
                        Text(doctor.doctorName ?? "")
                            .font(.subheadline)
                            .fontWeight(.bold)
                            .lineLimit(1)
                        Text(doctor.designation ?? "")
                            .font(.caption)
                            .foregroundColor(Color.gray)
                    }
                    .onTapGesture(perform: {
                        viewModel.navigateToEmptyView(screenTitle: "\(doctor.doctorName ?? "Doctor") details" )
                    })
                    .frame(width: 150)
                }
            }
        }
    }
}

struct ClinicCells: View {
    
    @EnvironmentObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 10) {
                ForEach(viewModel.clinics, id: \.self) { clinic in
                    VStack(alignment: .leading) {
                        Image("clinic")
                            .resizable()
                            .frame(width: 250, height: 120)
                        
                        VStack(alignment: .leading, spacing: 5) {
                            Text(clinic.clinicName ?? "")
                                .font(.headline)
                                .fontWeight(.bold)
                            Text(clinic.address ?? "")
                                .foregroundColor(Color.gray)
                            Spacer()
                        }
                        .padding(10)
                        
                    }
                    .onTapGesture(perform: {
                        viewModel.navigateToEmptyView(screenTitle: "\(clinic.clinicName ?? "Clinic") details")
                    })
                    .frame(width: 250)
                    .padding(.bottom, 10)
                    .background(Color.lightGrey)
                    .cornerRadius(20)
                }
            }
        }
    }
}

struct MedicalRecordsCells: View {
    
    @EnvironmentObject var viewModel: HapiHomeViewModel
    
    var body: some View {
        VStack(alignment: .center) {
            ForEach(viewModel.medicalRecords, id: \.self) { medicalRecord in
                VStack(alignment: .leading) {
                    HStack(alignment: .center) {
                        Image(systemName: "wallet.pass.fill")
                            .foregroundColor(Color.customCyan)
                        Text(medicalRecord)
                            .font(.headline)
                            .fontWeight(.bold)
                        Spacer()
                        Image(systemName: "chevron.right")
                            .foregroundColor(Color.customCyan)
                    }
                    .padding(10)
                    Divider()
                }
                .onTapGesture(perform: {
                    viewModel.navigateToEmptyView(screenTitle: medicalRecord)
                })
                .padding(.bottom, 10)
            }
            Button(action: { viewModel.navigateToEmptyView(screenTitle: "All Records") }, label: {
                Text("VIEW ALL RECORDS")
                    .padding()
                    .font(.headline)
                    .foregroundColor(Color.customCyan)
                    .overlay(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.customCyan, lineWidth: 2)
                    )
            })
        }
        .padding()
    }
}

struct HapiHomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HapiHomeScreen(viewModel: HapiHomeViewModel())
    }
}
