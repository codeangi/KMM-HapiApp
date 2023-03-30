package com.deepak.myapplication.android

import androidx.lifecycle.ViewModel
import com.deepak.myapplication.model.CareTeamData

class MainActivityViewModel : ViewModel() {

    var selectedCareTeamDoctor: CareTeamData? = null
}