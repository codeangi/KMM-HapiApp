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
                let monthYear = "\(months.month) \(months.year)"
                Text(monthYear)
                    .font(.title)
                    .fontWeight(.semibold)
                    .padding(.vertical)
                    .frame(maxWidth: .infinity, alignment: .leading)
                Divider()
                ForEach(months.slotData, id: \.self) { slotData in
                    HStack(alignment: .top) {
                        VStack(alignment: .center, spacing: 5) {
                            let weekDay = slotData.dayAndTimeMap?.first?.weekDay
                            let weekDate = slotData.dayAndTimeMap?.first?.date
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
                                let timeArray = slotData.dayAndTimeMap?.second as? [String]
                                ForEach(timeArray ?? [], id: \.self) { timeData in
                                    VStack {
                                        Text(timeData)
                                            .foregroundColor(Color.customCyan)
                                            .fontWeight(.bold)
                                    }
                                    .padding()
                                    .background(Color.lightGrey.opacity(0.6))
                                    .cornerRadius(10)
                                    .onTapGesture {
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
