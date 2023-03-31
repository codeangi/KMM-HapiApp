//
//  AppointmentViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 29/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

enum AppointmentScreens {
    case careTeam
    case careDetail
    case reason
    case type
    case location
    case dateTime
    case review
    case scheduled
    case empty
    
    var progressCount: CGFloat {
        switch self {
        case .reason:
            return 1.0
        case .type:
            return 2.0
        case .location:
            return 3.0
        case .dateTime:
            return 4.0
        case .review:
            return 5.0
        case .scheduled:
            return 6.0
        default:
            return 6.0
        }
    }
}

class AppointmentViewModel: ObservableObject {
    
    @Published var path = [AppointmentScreens]()
    
    @Published var past = [AppointmentModel]()
    @Published var upcoming = [AppointmentModel]()
    @Published var current = [AppointmentModel]()
    @Published var careTeam = [CareTeamModel]()
    @Published var reasons = [String]()
    @Published var types = [TypeModel]()
    @Published var locations = [LocationsModel]()

    init() {
        setPastAppointments()
        setUpcomingAppointments()
        setCurrentAppointments()
        setCareTeam()
        setReasons()
        setTypes()
        setLocations()
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
    
    func setCareTeam() {
        let c1 = CareTeamModel(name: "Dr.Leslie Crona, RN", description: "Neuropathology\nMeasure Wellness Llc")
        let c2 = CareTeamModel(name: "Dr.Betsey Kemmer, PHS", description: "General Medicine\nFamily and Community Solutions Llc")
        careTeam = [c1, c2]
    }
    
    func setReasons() {
        reasons = ["Back pain", "Headache", "Migraine"]
    }
    
    func setTypes() {
        let t1 = TypeModel(typeIcon: "building", typeName: "Office Visit")
        let t2 = TypeModel(typeIcon: "video", typeName: "Video Visit")
        types = [t1, t2]
    }
    
    func setLocations() {
        let l1 = LocationsModel(place: "Measured Wellness Llc", address: "47 Seaverns Ave #5\nBrookline, MA 0383645323", availableOn: "Attending hours:\n8 AM - 2 PM - Mon, Tue, Wed, Thu, Fri")
        locations = [l1]
    }
    
    func appendScreen(screenType: AppointmentScreens) {
        path.append(screenType)
    }
    
    func popScreen() {
        path.removeLast()
    }
    
    func navigateToRoot() {
        path = []
    }
}
