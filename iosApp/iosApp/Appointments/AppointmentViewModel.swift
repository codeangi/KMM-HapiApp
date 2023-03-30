//
//  AppointmentViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 29/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

enum AppointmentType: String  {
    case past = "Past"
    case upcoming = "Upcoming"
    case present = "Today"
}

class AppointmentViewModel: ObservableObject {
    @Published var past = [AppointmentModel]()
    @Published var upcoming = [AppointmentModel]()
    @Published var current = [AppointmentModel]()
    
    init() {
        setPastAppointments()
        setUpcomingAppointments()
        setCurrentAppointments()
    }
    
    func setPastAppointments() {
        let a1 = AppointmentModel(type: "Headache", date: "Tuesday, Mar 14 at 8:30 PM", doctor: "Dr.Leslie Crona, RN", careName: "Cambridge Hospital")
        let a2 = AppointmentModel(type: "Fever", date: "Tuesday, Mar 14 at 8:30 PM", doctor: "Dr.Leslie Crona, RN", careName: "Cambridge Hospital")
        past = [a1, a2]
    }
    func setUpcomingAppointments() {
        let a1 = AppointmentModel(type: "Headache", date: "Tuesday, Mar 14 at 8:30 PM", doctor: "Dr.Leslie Crona, RN", careName: "Cambridge Hospital")
        let a2 = AppointmentModel(type: "Fever", date: "Tuesday, Mar 14 at 8:30 PM", doctor: "Dr.Leslie Crona, RN", careName: "Cambridge Hospital")
        upcoming = [a1, a2]
    }
    func setCurrentAppointments() {
        let a1 = AppointmentModel(type: "Headache", date: "Tuesday, Mar 14 at 8:30 PM", doctor: "Dr.Leslie Crona, RN", careName: "Cambridge Hospital")
        let a2 = AppointmentModel(type: "Fever", date: "Tuesday, Mar 14 at 8:30 PM", doctor: "Dr.Leslie Crona, RN", careName: "Cambridge Hospital")
        current = [a1, a2]
    }
}
