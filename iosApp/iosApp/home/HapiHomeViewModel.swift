//
//  HapiHomeViewModel.swift
//  iosApp
//
//  Created by Swasthik K S on 28/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

class HapiHomeViewModel: ObservableObject {
    
    @Published var path: [String] = []
    @Published var options: [String] = []
    @Published var doctors: [DoctorModel] = []
    @Published var clinics: [ClinicModel] = []
    @Published var medicalRecords: [String] = []
    @Published var emptyScreenTitle: String = ""
    
    init() {
        setOptions()
        setDoctors()
        setClinics()
        setMedicalRecords()
    }
    
    func setOptions() {
        options = ["Medical Records", "Insurance Card", "Doctor Records", "Urgent Contact"]
    }
    
    func setDoctors() {
        let doctor1 = DoctorModel(doctorName: "Dr.Leslie Crona", designation: "Registered Nurse")
        let doctor2 = DoctorModel(doctorName: "Dr.Betsey Kemmer", designation: "Doctor of Science")

        doctors = [doctor1, doctor2]
    }
    
    func setClinics() {
        let clinic1 = ClinicModel(name: "Franciscan Hospital for Children", address: Address(addressLine1: "30 Warren St", city: "Boston", id: "MA 023635826"))
        let clinic2 = ClinicModel(name: "Cambridge Hospital", address: Address(addressLine1: "1494 Cambridge", city: "Somerville", id: "MA 2834829237"))
        
        clinics = [clinic1, clinic2]
    }
    
    func setMedicalRecords() {
        medicalRecords = ["Immunizations", "Test Results", "My Conditions"]
    }
    
    func navigateToEmptyView(screenTitle: String) {
        path.append(screenTitle)
    }
}
