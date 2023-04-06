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
    @Published var isLoading: Bool = true
    
    @Published var past = [AppointmentScheduleData]()
    @Published var upcoming = [AppointmentScheduleData]()
    @Published var current = [AppointmentScheduleData]()
    @Published var careTeam = [CareTeamData]()
    @Published var reasons = [String]()
    @Published var types = [TypeModel]()
    @Published var dateTime = [TimeSlotData]()
    @Published var screenWidth = UIScreen.main.bounds.width
    @Published var selectedPractitionerId = ""
    @Published var monthSlotData = [TimeSlotData]()
    
    @Published var selectedReason: String = ""
    @Published var selectedDoctorName: String = ""
    @Published var selectedLocation: String = ""
    @Published var selectedDateString: String = ""
    @Published var note: String = ""
    @Published var selectedResource: Resource?
        
    private var appointmentUseCase = KMPAppointmentUseCaseHelper().appointmentUseCase
    
    func fetchAppointments() {
        
        appointmentUseCase.getPatientTodaysAppointments { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<AppointmentScheduleData> else { return }
            guard let result = request.result as? [AppointmentScheduleData] else { return }
            
            self.current = result
        }
        
        appointmentUseCase.getPatientFutureAppointments { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<AppointmentScheduleData> else { return }
            guard let result = request.result as? [AppointmentScheduleData] else { return }
            
            self.upcoming = result
        }
        
        appointmentUseCase.getPatientPastAppointments { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<AppointmentScheduleData> else { return }
            guard let result = request.result as? [AppointmentScheduleData] else { return }
            
            self.past = result
            
            DispatchQueue.main.async {
                self.isLoading = false
            }
        }
    }
    
    func setCareTeam() {
        
        appointmentUseCase.getMyCareTeamData { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<CareTeamData> else { return }
            guard let result = request.result as? [CareTeamData] else { return }
            
            self.careTeam = result
            
            DispatchQueue.main.async {
                self.isLoading = false
            }
        }
    }
    
    func setReasons() {
        reasons = ["Back pain", "Headache", "Migraine"]
        
        DispatchQueue.main.async {
            self.isLoading = false
        }
    }
    
    func setTypes() {
        let t1 = TypeModel(typeIcon: "building", typeName: "Office Visit")
        let t2 = TypeModel(typeIcon: "video", typeName: "Video Visit")
        types = [t1, t2]
        
        DispatchQueue.main.async {
            self.isLoading = false
        }
    }
    
    func setDateTime() {
        monthSlotData = []
        appointmentUseCase.getAppointmentSlots(practitionerId: selectedPractitionerId) { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<TimeSlotData> else { return }
            guard let result = request.result as? [TimeSlotData] else { return }
            
            var monthYear: String = ""
            for timeSlotData in result {
                let monthYearString = "\(timeSlotData.month ?? "") \(timeSlotData.year ?? "")"
                if monthYear != monthYearString {
                    self.monthSlotData.append(timeSlotData)
                    monthYear = monthYearString
                } else {
                    
                }
            }
            self.dateTime = result
            
            DispatchQueue.main.async {
                self.isLoading = false
            }
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
        selectedDoctorName = appointment.doctorName ?? ""
        selectedReason = appointment.symptoms ?? ""
        selectedLocation = appointment.location ?? ""
        selectedDateString = appointment.appointmentDate ?? ""
        appendScreen(screenType: .review(isProgressNeeded: false))
    }
    
    func clearReviewData() {
        selectedDoctorName = ""
        selectedReason = ""
        selectedLocation = ""
        selectedDateString = ""
        note = ""
    }
    
    func getCareTeamData() -> CareTeamData {
        return careTeam.first(where: { $0.practitionerId == selectedPractitionerId }) ?? careTeam[0]
    }
    
    func getNoteValue(isProgressNeeded: Bool) -> String {
        if note == "" {
            return isProgressNeeded ? "Add notes to share with your care team ahead of your visit" : "No Notes Added"
        }
        return self.note
    }
    
    func scheduleAppointment() {
        let serviceType = ServiceType(coding: Coding(system: nil, code: "code", display: selectedReason))
        guard let selectedResource = selectedResource else { return }
        
        let bookingResource = BookingResource(id: selectedResource.id, resourceType: selectedResource.resourceType, comment: note, serviceType: serviceType, participant: selectedResource.participant, slot: selectedResource.slot, start: selectedResource.start, end: selectedResource.end)
        
        
        appointmentUseCase.bookAppointment(resource: bookingResource) { appRequest, error in
            guard let appRequest = appRequest, let _ = appRequest as? AppRequestResult<Resource> else { return }
            self.isLoading = false
            self.appendScreen(screenType: .scheduled)
        }
    }
}
