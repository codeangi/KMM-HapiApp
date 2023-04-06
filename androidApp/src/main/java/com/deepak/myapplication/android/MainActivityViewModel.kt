package com.deepak.myapplication.android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.deepak.myapplication.AppConstant.ADD_NOTES_DEFAULT_TEXT
import com.deepak.myapplication.model.CareTeamData
import com.deepak.myapplication.model.Resource
import com.deepak.myapplication.model.SelectedAppointmentData

class MainActivityViewModel : ViewModel() {

    var selectedCareTeamDoctor: CareTeamData? = null

    var selectedAppointmentData: SelectedAppointmentData = SelectedAppointmentData()

    var showBottomNavBar = mutableStateOf(true)

    var responseDataForBooking: Resource? = null

    var addedNotesState = mutableStateOf(ADD_NOTES_DEFAULT_TEXT)

}