//
//  HapiHomeViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 28/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

class HapiHomeViewModel: ObservableObject {
    
    @Published var path: [String] = []
    @Published var options: [String] = []
    @Published var doctors: [DoctorData] = []
    @Published var clinics: [ClinicData] = []
    @Published var medicalRecords: [String] = []
    @Published var emptyScreenTitle: String = ""
    
    private var homeUseCase = KMPHomeUseCaseHelper().homeUseCase
    
    init() {
        accessToken()
        setOptions()
        setDoctors()
        setClinics()
        setMedicalRecords()
    }
    
    func accessToken() {
        homeUseCase.getAccessToken { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<DoctorData> else { return }
            guard let result = request.result as? [DoctorData] else { return }
        }
    }
    
    func setOptions() {
        options = ["Medical Records", "Insurance Card", "Doctor Records", "Urgent Contact"]
    }
    
    func setDoctors() {
       
        homeUseCase.getDoctorsData { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<DoctorData> else { return }
            guard let result = request.result as? [DoctorData] else { return }
            
            self.doctors = result
        }
    }
    
    func setClinics() {
        
        homeUseCase.getClinicDetails { appRequest, error in
            guard let appRequest = appRequest, let request = appRequest as? AppRequestListResult<ClinicData> else { return }
            guard let result = request.result as? [ClinicData] else { return }
            
            self.clinics = result
        }
    }
    
    func setMedicalRecords() {
        medicalRecords = ["Immunizations", "Test Results", "My Conditions"]
    }
    
    func navigateToEmptyView(screenTitle: String) {
        path.append(screenTitle)
    }
}
