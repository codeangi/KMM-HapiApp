package com.deepak.myapplication

import com.deepak.myapplication.local.DatabaseDriverFactory
import io.ktor.client.*
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseDriverFactory() }
}

actual fun getNetworkClient(): HttpClient {
    return HttpClient {  }
}