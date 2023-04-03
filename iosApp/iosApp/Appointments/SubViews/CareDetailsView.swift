//
//  CareDetailsView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CareDetailsView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack {
            ZStack(alignment: .topLeading) {
                ScrollView {
                    TopView()
                    DetailsView()
                    Divider()
                        .padding(.horizontal)
                    AboutView()
                    keyValueDetailView()
                        .environmentObject(viewModel)
                    Divider()
                    LocationView()
                        .padding(.bottom, 20)
                }
                .ignoresSafeArea()
                ZBackButton()
                    .environmentObject(viewModel)
            }
            .navigationBarHidden(true)
        }
        Button("SCHEDULE APPOINTMENT") {
            viewModel.appendScreen(screenType: .reason)
        }
        .padding()
        .frame(width: viewModel.screenWidth - 60, height: 50)
        .font(.headline)
        .fontWeight(.bold)
        .foregroundColor(Color.white)
        .background(Color.customCyan)
        .cornerRadius(15)
    }
}

struct ZBackButton: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        Image(systemName: "chevron.left")
            .resizable()
            .frame(width: 15, height: 15)
            .padding()
            .foregroundColor(.black)
            .background(Color.white.opacity(0.6))
            .cornerRadius(30)
            .offset(x: 10, y: 10)
            .onTapGesture {
                viewModel.popScreen()
            }
    }
}

struct TopView: View {
    var body: some View {
        ZStack(alignment: .bottomLeading) {
            VStack {
                Image("clinic")
                    .resizable()
                    .frame(height: 250)
            }
            
            Image("doctor")
                .resizable()
                .scaledToFill()
                .frame(width: 80, height: 80)
                .cornerRadius(40)
                .overlay(
                    RoundedRectangle(cornerRadius: 40)
                        .stroke(Color.white, lineWidth: 3)
                )
                .offset(x: 40, y: 40)
        }
    }
}

struct DetailsView: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("Dr.Leslie Crona, RN")
                .font(.title)
                .fontWeight(.bold)
            Text("Accepting")
                .foregroundColor(.blue)
                .font(.headline)
            Text("Specialities")
                .font(.title3)
                .fontWeight(.semibold)
            Text("Neuropathology")
                .font(.title3)
        }
        .padding(20)
        .padding(.top, 30)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

struct AboutView: View {
    
    @State var aboutExpanded: Bool = true
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            HStack {
                Text("About")
                    .font(.title3)
                    .fontWeight(.bold)
                Spacer()
                Image(systemName: aboutExpanded ? "chevron.down" : "chevron.up")
                    .onTapGesture {
                        aboutExpanded = !aboutExpanded
                    }
            }
            if aboutExpanded {
                Text("This is a test data. The actual about of the doctor will be available soon. This is a test data. The actual about of the doctor will be available soon. This is a test data. The actual about of the doctor will be available soon")
                    .font(.headline)
                    .fontWeight(.regular)
            }
            Divider()
                .padding(.top, 20)
        }
        .padding(20)
    }
}

struct keyValueView: View {
    
    var screenWidth = UIScreen.main.bounds.width
    var title: String
    var value: String
    var isColoredValue: Bool = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(title)
                .font(.headline)
                .fontWeight(.bold)
                .foregroundColor(.black.opacity(0.6))
            Text(value)
                .foregroundColor(isColoredValue ? Color.customCyan : Color.black)
                .font(.headline)
                .fontWeight(isColoredValue ? .bold : .regular)
        }
        .frame(width: (screenWidth - 40) / 2, alignment: .leading )
    }
}

struct keyValueDetailView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 40) {
            HStack(alignment: .top) {
                keyValueView(title: "Gender", value: "Female")
                keyValueView(title: "Languages", value: "Spanish")
            }
            HStack(alignment: .top) {
                keyValueView(title: "Years in Practice", value: "3")
                keyValueView(title: "Board Certification", value: "Registered Nurse")
            }
            HStack(alignment: .top) {
                keyValueView(title: "Group Affiliations", value: "0 PLACE", isColoredValue: true)
                keyValueView(title: "Hospital Affiliations", value: "0 HOSPITAL", isColoredValue: true)
            }
            HStack(alignment: .top) {
                keyValueView(title: "Medical school", value: "Boston University")
                keyValueView(title: "Residency", value: "CHA Cambridge Hospital")
            }
        }
        .padding(.horizontal, 20)
        .padding(.bottom, 20)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

struct LocationView: View {
    var body: some View {
        VStack(alignment: .leading) {
            Text("Locations")
                .font(.title3)
                .fontWeight(.bold)
            HStack {
                Image("clinic")
                    .resizable()
                    .frame(width: 60, height: 60)
                    .cornerRadius(10)
                VStack(alignment: .leading, spacing: 5) {
                    Text("Measured Wellness Llc")
                    Text("47 Seaverns Ave#5\nBrookline, MA 03838723")
                        .font(.subheadline)
                        .foregroundColor(.black.opacity(0.6))
                }
                .padding(.leading)
            }
        }
        .padding(.horizontal, 20)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

struct CareDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            CareDetailsView().environmentObject(AppointmentViewModel())
        }
        
    }
}
