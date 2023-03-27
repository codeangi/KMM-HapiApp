package com.deepak.myapplication.android

import android.app.Application
import com.deepak.myapplication.android.di.appModule
import com.deepak.myapplication.di.initKoin
import org.koin.android.ext.koin.androidContext

class HapiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
          androidContext(this@HapiApp)
            modules(appModule)
        }
    }
}