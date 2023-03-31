//
//  TypeView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct TypeView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            ProgressBar(currentProgress: AppointmentScreens.type.progressCount)
            VStack(alignment: .leading) {
                Text("What is the reason for your visit?")
                    .font(.title)
                    .fontWeight(.bold)
                List {
                    ForEach(viewModel.types, id: \.self) { type in
                        
                        HStack {
                            Image(systemName: type.typeIcon)
                                .foregroundColor(Color.black.opacity(0.6))
                            Text(type.typeName)
                                .font(.headline)
                                .padding(10)
                            Spacer()
                            Image(systemName: "chevron.right")
                                .foregroundColor(Color.black.opacity(0.6))
                        }
                        .onTapGesture {
                            viewModel.path.append(AppointmentScreens.location)
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
        .navigationBarItems(trailing: navBarCancelButton(viewModel: viewModel))
        .padding()
    }
}

struct TypeView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            TypeView().environmentObject(AppointmentViewModel())
        }
    }
}
