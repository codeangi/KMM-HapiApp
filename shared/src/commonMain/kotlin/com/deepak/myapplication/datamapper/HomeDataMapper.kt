package com.deepak.myapplication.datamapper

import com.deepak.myapplication.model.ClinicData
import com.deepak.myapplication.model.DoctorData

class HomeDataMapper() {

    fun getClinicDataFromResponse(): List<ClinicData> {
        return listOf(
            ClinicData("Franciscan Hospital for Children", "30 Warren St Boston, MA 021353602"),
            ClinicData("Cambridge Hospital", "33 Tower St, Somerville, MA 02143, United States")
        )
    }

    fun getDoctorsDataFromResponse(): List<DoctorData> {
        return listOf(
            DoctorData("Dr. Leslie Crona", "Registered Nurse"),
            DoctorData("Dr. Betsey Kemmer", "Doctor of Science")
        )
    }
}