//
//  TabBarView.swift
//  iosApp
//
//  Created by Swasthik K S on 27/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct TabBarView: View {
    var body: some View {
        TabView {
            HapiHomeScreen(viewModel: HapiHomeViewModel())
                .tabItem {
                    Image(systemName: "house")
                    Text("Home")
                }
            MessageScreen()
                .tabItem {
                    Image(systemName: "message")
                    Text("Message")
                }
            AppointmentScreen()
                .tabItem {
                    Image(systemName: "calendar")
                    Text("Appointment")
                }
            MedicationScreen()
                .tabItem {
                    Image(systemName: "pill")
                    Text("Medication")
                }
            ProfileScreen()
                .tabItem {
                    Image(systemName: "person.circle")
                    Text("Profile")
                }
        }
    }
}
