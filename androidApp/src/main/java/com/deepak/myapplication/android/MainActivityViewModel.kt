package com.deepak.myapplication.android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.deepak.myapplication.model.CareTeamData
import com.deepak.myapplication.model.SelectedAppointmentData

class MainActivityViewModel : ViewModel() {

    var selectedCareTeamDoctor: CareTeamData? = null

    var selectedAppointmentData: SelectedAppointmentData = SelectedAppointmentData()

    var showBottomNavBar = mutableStateOf(true)

}