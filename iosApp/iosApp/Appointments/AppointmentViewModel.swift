//
//  AppointmentViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 29/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

enum AppointmentScreens: Hashable {
    case careTeam
    case careDetail(careData: CareTeamData)
    case reason
    case type
    case location
    case dateTime
    case review(isProgressNeeded: Bool)
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
        case .review(_):
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
    
    @Published var past = [AppointmentScheduleData]()
    @Published var upcoming = [AppointmentScheduleData]()
    @Published var current = [AppointmentScheduleData]()
    @Published var careTeam = [CareTeamData]()
    @Published var reasons = [String]()
    @Published var types = [TypeModel]()
    @Published var dateTime = [MonthModel]()
    @Published var screenWidth = UIScreen.main.bounds.width
    @Published var selectedCareTeamIndex: Int = 0
    @Published var note: String = ""
    @Published var selectedAppointmentData: SelectedAppointmentData = SelectedAppointmentData(doctorName: "", reason: "", appointmentType: "", appointmentLocationAddress: "", timeSlotData: "")
    
    private var appointmentUseCase = KMPAppointmentUseCaseHelper().appointmentUseCase

    init() {
        setPastAppointments()
        setUpcomingAppointments()
        setCurrentAppointments()
        setCareTeam()
        setReasons()
        setTypes()
        setDateTime()
    }
    
    func setPastAppointments() {
        appointmentUseCase.getPatientPastAppointments { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<AppointmentScheduleData> else { return }
            guard let result = request.result as? [AppointmentScheduleData] else { return }
            
            self.past = result
        }
    }
    func setUpcomingAppointments() {
        
        appointmentUseCase.getPatientFutureAppointments { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<AppointmentScheduleData> else { return }
            guard let result = request.result as? [AppointmentScheduleData] else { return }
            
            self.upcoming = result
        }
    }
    func setCurrentAppointments() {
        
        appointmentUseCase.getPatientAppointmentSchedules { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<AppointmentScheduleData> else { return }
            guard let result = request.result as? [AppointmentScheduleData] else { return }
            
            self.current = result
        }
    }
    
    func setCareTeam() {
        
        appointmentUseCase.getMyCareTeamData { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<CareTeamData> else { return }
            guard let result = request.result as? [CareTeamData] else { return }
            
            self.careTeam = result
        }
    }
    
    func setReasons() {
        reasons = ["Back pain", "Headache", "Migraine"]
    }
    
    func setTypes() {
        let t1 = TypeModel(typeIcon: "building", typeName: "Office Visit")
        let t2 = TypeModel(typeIcon: "video", typeName: "Video Visit")
        types = [t1, t2]
    }
    
    func setDateTime() {
        let t1: [String] = ["6:30 PM", "7:30 PM", "9:30 PM", "10:30 PM"]
        let t2: [String] = ["6:30 PM", "9:30 PM", "10:30 PM"]
        let t3: [String] = ["7:30 PM", "8:30 PM"]
        
        let d1 = DateModel(weekDay: "Tuesday", weekDate: "14", time: t2)
        let d2 = DateModel(weekDay: "Thursday", weekDate: "16", time: t3)
        let d3 = DateModel(weekDay: "Tuesday", weekDate: "21", time: t2)
        let d4 = DateModel(weekDay: "Monday", weekDate: "03", time: t1)
        let d5 = DateModel(weekDay: "Wednesday", weekDate: "05", time: t3)
        let d6 = DateModel(weekDay: "Friday", weekDate: "07", time: t1)
        
        let m1 = MonthModel(month: "April", year: "2023", dayAndTimeMap: [d1,d2,d3])
        let m2 = MonthModel(month: "May", year: "2023", dayAndTimeMap: [d4,d5,d6])
        
        dateTime = [m1, m2]
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
    
    func didTapAppointment(appointment: AppointmentScheduleData) {
        selectedAppointmentData.reason = appointment.symptoms
        selectedAppointmentData.doctorName = appointment.doctorName
        selectedAppointmentData.appointmentLocationAddress = appointment.location
        selectedAppointmentData.timeSlotData = appointment.appointmentDate
        appendScreen(screenType: .review(isProgressNeeded: false))
    }
    
    func clearSelectedData() {
        selectedAppointmentData.reason = ""
        selectedAppointmentData.doctorName = ""
        selectedAppointmentData.appointmentLocationAddress = ""
        selectedAppointmentData.timeSlotData = ""
    }
}
