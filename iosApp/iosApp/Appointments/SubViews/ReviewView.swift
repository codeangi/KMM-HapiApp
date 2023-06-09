//
//  AppointmentReviewView.swift
//  iosApp
//
//  Created by Swasthik K S on 31/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ReviewView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    @State private var showBottomSheet = false
    var isProgressNeeded: Bool = true
    
    var body: some View {
        ZStack(alignment: .center) {
            ZStack(alignment: .bottom) {
                VStack(alignment: .leading) {
                    if isProgressNeeded {
                        ProgressBar(currentProgress: AppointmentScreens.review(isProgressNeeded: isProgressNeeded).progressCount)
                    }
                    VStack(alignment: .leading) {
                        if !isProgressNeeded {
                            Image(systemName: "xmark")
                                .resizable()
                                .frame(width: 20, height: 20)
                                .onTapGesture {
                                    viewModel.popScreen()
                                }
                        }
                        Text(isProgressNeeded ? "Appointment Review" : "Appointment")
                            .font(.system(size: 32))
                            .fontWeight(.medium)
                            .padding(.bottom, 10)
                        
                        if isProgressNeeded {
                            Text("This time is reserved for you until 6:39 PM.\nPlease complete scheduling by then.")
                                .font(.headline)
                                .fontWeight(.regular)
                                .padding(.bottom, 10)
                        }
                        
                        ScrollView(showsIndicators: false) {
                            ReviewCell(imageName: "person", title: "Provider", value: viewModel.selectedDoctorName)
                            ReviewCell(imageName: "cross.case", title: "Reason for Visit", value: viewModel.selectedReason)
                            ReviewCell(imageName: "mappin.and.ellipse", title: "Location", value: viewModel.selectedLocation)
                            ReviewCell(imageName: "clock", title: "Date and Time", value: viewModel.selectedDateString)
                            ReviewCell(imageName: "note.text", title: "Notes", value: viewModel.getNoteValue(isProgressNeeded: isProgressNeeded))
                                .onTapGesture {
                                    if isProgressNeeded {
                                        showBottomSheet = true
                                    }
                                }
                        }
                        .padding(.vertical)
                        .padding(.bottom, 20)
                    }
                }
                .sheet(isPresented: $showBottomSheet, content: {
                    VStack(alignment: .center, spacing: 20) {
                        Image(systemName: "xmark")
                            .resizable()
                            .frame(width: 20, height: 20)
                            .padding()
                            .padding(.top, 20)
                            .onTapGesture {
                                viewModel.note = ""
                                showBottomSheet = false
                            }
                        Text("Notes")
                            .font(.title)
                            .fontWeight(.semibold)
                            .padding(.horizontal)
                        TextEditor(text: $viewModel.note)
                            .foregroundColor(.black)
                            .padding()
                            .background(Color.customCyan.opacity(0.2))
                            .presentationDetents([.medium])
                            .scrollContentBackground(.hidden)
                            .cornerRadius(10)
                            .disabled(!isProgressNeeded)
                        if isProgressNeeded {
                            Button("SAVE") {
                                showBottomSheet = false
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
                    .padding()
                    
                })
                .navigationBarHidden(!isProgressNeeded)
                .navigationTitle("Schedule Appointment")
                .navigationBarTitleDisplayMode(.inline)
                .navigationBarBackButtonHidden(true)
                .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
                .navigationBarItems(trailing: navBarCancelButton(viewModel: viewModel))
                .padding()
                
                if isProgressNeeded {
                    Button("SCHEDULE APPOINTMENT") {
                        viewModel.isLoading = true
                        viewModel.scheduleAppointment()
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
            .opacity(viewModel.isLoading ? 0.3 : 1.0)
            if viewModel.isLoading {
                ProgressView()
            }
        }
        .disabled(viewModel.isLoading)
        
    }
}

struct ReviewCell: View {
    var imageName: String
    var title: String
    var value: String
    
    var body: some View {
        HStack(alignment: .top, spacing: 20) {
            VStack {
                Image(systemName: imageName)
                    .resizable()
                    .frame(width: 20, height: 20)
                    .foregroundColor(Color.customCyan)
            }
            .frame(width: 40, height: 40)
            .padding(10)
            .background(Color.lightGrey.opacity(0.5))
            .cornerRadius(30)
            VStack(alignment: .leading, spacing: 10) {
                Text(title)
                    .font(.headline)
                    .fontWeight(.bold)
                Text(value)
                    .font(.headline)
                    .fontWeight(.regular)
                    .foregroundColor(Color.black.opacity(0.6))
            }
            Spacer()
        }
        .padding(.vertical, 10)
        Divider()
    }
}

struct ReviewView_Previews: PreviewProvider {
    static var previews: some View {
        ReviewView().environmentObject(AppointmentViewModel())
    }
}
