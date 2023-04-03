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
    
    var body: some View {
        ZStack(alignment: .bottom) {
            VStack(alignment: .leading) {
                ProgressBar(currentProgress: AppointmentScreens.review.progressCount)
                VStack(alignment: .leading) {
                    Text("Appointment Review")
                        .font(.system(size: 32))
                        .fontWeight(.medium)
                        .padding(.bottom, 10)
                    Text("This time is reserved for you until 6:39 PM.\nPlease complete scheduling by then.")
                        .font(.headline)
                        .fontWeight(.regular)
                        .padding(.bottom, 10)
                    ScrollView(showsIndicators: false) {
                        ReviewCell(imageName: "clinic", title: "Provider", value: "Dr.Leslie Crona, RN", isProvider: true)
                        ReviewCell(imageName: "cross.case", title: "Reason for Visit", value: "Back pain")
                        ReviewCell(imageName: "mappin.and.ellipse", title: "Location", value: "Measured Wellness Llc, 47 Seaverns Ave #5 Brookline, MA 03838723")
                        ReviewCell(imageName: "clock", title: "Date and Time", value: "Friday, March 31st 10:30 PM")
                        ReviewCell(imageName: "note.text", title: "Notes", value: "Add notes to share with your care team ahead of your visit")
                    }
                    .padding(.vertical)
                }
            }
            .navigationTitle("Schedule Appointment")
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
            .navigationBarItems(trailing: navBarCancelButton(viewModel: viewModel))
            .padding()
            Button("SCHEDULE APPOINTMENT") {
                viewModel.appendScreen(screenType: .scheduled)
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
}

struct ReviewCell: View {
    var imageName: String
    var title: String
    var value: String
    var isProvider: Bool = false
    
    var body: some View {
        HStack(alignment: .top, spacing: 20) {
            if isProvider {
                Image(imageName)
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(width: 60, height: 60)
                    .cornerRadius(30)
            } else {
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
            }
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
