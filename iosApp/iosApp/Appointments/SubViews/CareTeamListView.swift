//
//  CareTeamListView.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CareTeamListView: View {
    
    @EnvironmentObject var viewModel: AppointmentViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            VStack(alignment: .leading) {
                Text("Who would you like to see?")
                    .font(.title)
                    .fontWeight(.semibold)
                    .padding(.bottom, 30)
                Text("My care team")
                    .font(.title3)
                    .fontWeight(.semibold)
                List {
                    ForEach(viewModel.careTeam, id: \.self) { careTeam in
                        
                        HStack {
                            Image("doctor")
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                                .frame(width: 60, height: 60)
                                .cornerRadius(30)
                                .padding(.trailing, 20)
                                .foregroundColor(Color.customCyan)
                            VStack(alignment: .leading, spacing: 5) {
                                Text(careTeam.doctorName ?? "")
                                    .font(.title3)
                                    .fontWeight(.semibold)
                                Text(careTeam.designation ?? "")
                                    .font(.subheadline)
                                    .foregroundColor(.black.opacity(0.5))
                                Text(careTeam.hospitalLocation ?? "")
                                    .font(.subheadline)
                                    .foregroundColor(.black.opacity(0.5))
                            }
                            Spacer()
                            Image(systemName: "chevron.right")
                                .foregroundColor(Color.black.opacity(0.6))
                        }
                        
                        .padding(.vertical, 10)
                        .onTapGesture {
                            viewModel.selectedCareTeamIndex = viewModel.careTeam.firstIndex(of: careTeam) ?? 0
                            viewModel.appendScreen(screenType: .careDetail(careData: careTeam))
                        }
                    }
                }
                .padding(.vertical, 20)
                .listStyle(.plain)
                .scrollContentBackground(.hidden)
            }
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: navBarBackButton(viewModel: viewModel))
        }
        .padding()
    }
}

struct CareTeamListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationStack {
            CareTeamListView().environmentObject(AppointmentViewModel())
        }
        
    }
}
