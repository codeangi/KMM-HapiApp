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
            }
            Spacer()
        }
        .navigationTitle("Schedule Appointment")
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
        .navigationBarItems(trailing: navBarCancelButton(viewModel: viewModel))
        .padding()
    }
}

    struct DateTimeView_Previews: PreviewProvider {
        static var previews: some View {
            NavigationStack {
                DateTimeView().environmentObject(AppointmentViewModel())
            }
        }
    }
