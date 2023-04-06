//
//  DateTimeView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DateTimeView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            ProgressBar(currentProgress: AppointmentScreens.dateTime.progressCount)
            VStack(alignment: .leading) {
                Text("When would you be like to be seen?")
                    .font(.title)
                    .fontWeight(.bold)
                if viewModel.isLoading {
                    VStack {
                        Spacer()
                        ProgressView()
                        Spacer()
                    }
                    .frame(maxWidth: .infinity)
                } else {
                    DateTimeCard().environmentObject(viewModel)
                }
                
                
            }
        }
        .onAppear() {
            viewModel.isLoading = true
            viewModel.setDateTime()
        }
        .navigationTitle("Schedule Appointment")
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
        .navigationBarItems(trailing: navBarCancelButton(viewModel: viewModel))
        .padding()
    }
}

struct DateTimeCard: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            Divider()
            ForEach(viewModel.dateTime, id:\.self) { timeSlotData in
                let monthYear = "\(timeSlotData.month ?? "") \(timeSlotData.year ?? "")"
                if viewModel.monthSlotData.contains(timeSlotData) {
                    Text(monthYear)
                        .font(.title)
                        .fontWeight(.semibold)
                        .padding(.vertical)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Divider()
                }
                HStack(alignment: .top) {
                    VStack(alignment: .center, spacing: 5) {
                        let weekDay = timeSlotData.dayAndTimeMap?.first?.weekDay
                        let weekDate = timeSlotData.dayAndTimeMap?.first?.date
                        Text(weekDay ?? "")
                            .font(.headline)
                            .fontWeight(.semibold)
                        Text(weekDate ?? "")
                            .font(.title2)
                            .fontWeight(.bold)
                    }
                    .frame(width: 50, alignment: .leading)
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(alignment: .center) {
                            let timeArray = timeSlotData.dayAndTimeMap?.second as? [TimeWithResponseData]
                            ForEach(timeArray ?? [], id: \.self) { timeData in
                                VStack {
                                    Text(timeData.time ?? "")
                                        .foregroundColor(Color.customCyan)
                                        .fontWeight(.bold)
                                }
                                .padding()
                                .background(Color.lightGrey.opacity(0.6))
                                .cornerRadius(10)
                                .onTapGesture {
                                    let dateData = timeSlotData.dayAndTimeMap?.first
                                    let dateString = "\(dateData?.weekDay ?? ""), \(timeSlotData.month ?? "") \(dateData?.date ?? "") at \(timeData.time ?? "")"
                                    viewModel.selectedDateString = dateString
                                    if let response = timeData.response {
                                        viewModel.selectedResource = response
                                    }
                                    viewModel.appendScreen(screenType: .review(isProgressNeeded: true))
                                }
                            }
                        }
                    }
                }
                .padding(.vertical, 10)
                Divider()
            }
        }
    }
}

struct DateTimeView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            DateTimeView().environmentObject(AppointmentViewModel())
        }
    }
}
