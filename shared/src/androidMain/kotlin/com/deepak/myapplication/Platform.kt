package com.deepak.myapplication

import com.deepak.myapplication.local.DatabaseDriverFactory
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.koin.dsl.module


actual fun platformModule() = module {
        single { DatabaseDriverFactory(get()) }
}
actual fun getNetworkClient(): HttpClient {
    return HttpClient(OkHttp) {
        engine {

        }
    }
}