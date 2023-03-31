//
//  ReasonView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ReasonView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
            VStack(alignment: .leading) {
                ProgressBar(currentProgress: AppointmentScreens.reason.progressCount)
                VStack(alignment: .leading) {
                    Text("What is the reason for your visit?")
                        .font(.title)
                        .fontWeight(.bold)
                    List {
                        ForEach(viewModel.reasons, id: \.self) { reason in
                            
                                HStack {
                                    Text(reason)
                                        .font(.headline)
                                    .padding(10)
                                    Spacer()
                                    Image(systemName: "chevron.right")
                                        .foregroundColor(Color.black.opacity(0.6))
                                }
                                .onTapGesture {
                                    viewModel.appendScreen(screenType: .type)
                                }
                        }
                    }
                    .padding(.vertical, 10)
                    .listStyle(.plain)
                    .scrollContentBackground(.hidden)
                }
            }
            .navigationTitle("Schedule Appointment")
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
            .padding()

    }
}

struct ReasonView_Previews: PreviewProvider {
    static var previews: some View {
        ReasonView().environmentObject(AppointmentViewModel())
    }
}
