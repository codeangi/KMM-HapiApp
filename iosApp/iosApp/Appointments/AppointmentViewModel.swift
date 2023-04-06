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
    @Published var dateTime = [SlotModel]()
    @Published var screenWidth = UIScreen.main.bounds.width
    @Published var selectedPractitionerId = ""
    @Published var note: String = ""
    @Published var selectedAppointmentData: SelectedAppointmentData = SelectedAppointmentData(doctorName: "", reason: "", appointmentType: "", appointmentLocationAddress: "", timeSlotData: "")
    
    private var appointmentUseCase = KMPAppointmentUseCaseHelper().appointmentUseCase

    init() {
        setPastAppointments()
        setUpcomingAppointments()
        setCurrentAppointments()
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
        
        appointmentUseCase.getPatientTodaysAppointments { appRequest, error in
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
        appointmentUseCase.getAppointmentSlots(practitionerId: selectedPractitionerId) { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<TimeSlotData> else { return }
            guard let result = request.result as? [TimeSlotData] else { return }
            
            var month: String = ""
            var year: String = ""
            var timeSlotDataArray: [TimeSlotData] = []
            var finalArray: [SlotModel] = []
            
            for data in result {
                if month == "" && year == "" {
                    month = data.month ?? ""
                    year = data.year ?? ""
                    timeSlotDataArray.append(data)
                } else if month == data.month && year == data.year {
                    timeSlotDataArray.append(data)
                } else {
                    let slotModel = SlotModel(month: month, year: year, slotData: timeSlotDataArray)
                    finalArray.append(slotModel)
                    month = data.month ?? ""
                    year = data.year ?? ""
                    timeSlotDataArray = []
                    timeSlotDataArray.append(data)
                }
            }
            let slotModel = SlotModel(month: month, year: year, slotData: timeSlotDataArray)
            finalArray.append(slotModel)
            self.dateTime = finalArray
        }
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
    
    func getCareTeamData() -> CareTeamData {
        return careTeam.first(where: { $0.practitionerId == selectedPractitionerId }) ?? careTeam[0]
    }
}
