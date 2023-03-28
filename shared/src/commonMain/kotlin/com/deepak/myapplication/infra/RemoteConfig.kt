package com.deepak.myapplication.infra

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false }

fun getNetworkClient(): HttpClient {
    return HttpClient {
        install(Logging)
        install(ContentNegotiation) {
            json(createJson())
        }
    }
}


