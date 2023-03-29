package com.deepak.myapplication.infra

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false }

fun getNetworkClient(): HttpClient {
    return HttpClient {
        install(Logging) {
            logger = CustomLogger
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(createJson())
        }
    }
}

private object  CustomLogger: Logger{
    private const val TAG = "HTTPCLIENT"
    override fun log(message: String) {
        println("$TAG: $message")
    }

}

