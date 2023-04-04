//
//  DateTimeView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct DateTimeView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            ProgressBar(currentProgress: AppointmentScreens.dateTime.progressCount)
            VStack(alignment: .leading) {
                Text("When would you be like to be seen?")
                    .font(.title)
                    .fontWeight(.bold)
                DateTimeCard().environmentObject(viewModel)
                
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

struct DateTimeCard: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            Divider()
            ForEach(viewModel.dateTime, id: \.self) { months in
                Text("\(months.month) \(months.year)")
                    .font(.title)
                    .fontWeight(.semibold)
                    .padding(.vertical)
                    .frame(maxWidth: .infinity, alignment: .leading)
                Divider()
                ForEach(months.dayAndTimeMap, id: \.self) { dates in
                    HStack(alignment: .top) {
                        VStack(alignment: .leading, spacing: 5) {
                            Text(dates.weekDay.prefix(3).uppercased())
                                .font(.headline)
                                .fontWeight(.semibold)
                            Text(dates.weekDate)
                                .font(.title2)
                                .fontWeight(.bold)
                        }
                        .frame(width: 50, alignment: .leading)
                        ScrollView(.horizontal, showsIndicators: false) {
                            HStack {
                                ForEach(dates.time, id: \.self) { time in
                                    VStack {
                                        Text(time)
                                            .foregroundColor(Color.customCyan)
                                            .fontWeight(.bold)
                                    }
                                    .padding()
                                    .background(Color.lightGrey.opacity(0.6))
                                    .cornerRadius(10)
                                    .onTapGesture {
                                        let slotData = "\(dates.weekDay), \(months.month) \(dates.weekDate) at \(time)"
                                        viewModel.selectedAppointmentData.timeSlotData = slotData
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
}

    struct DateTimeView_Previews: PreviewProvider {
        static var previews: some View {
            NavigationStack {
                DateTimeView().environmentObject(AppointmentViewModel())
            }
        }
    }
