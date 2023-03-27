package com.deepak.myapplication.android.di

import com.deepak.myapplication.android.feature.registration.UserRegistrationViewModel
import com.deepak.myapplication.local.DatabaseDriverFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        DatabaseDriverFactory(get())
    }
    viewModel{
        UserRegistrationViewModel(get())
    }
}