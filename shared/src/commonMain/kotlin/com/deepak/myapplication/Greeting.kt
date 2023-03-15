package com.deepak.myapplication

import io.ktor.client.*
import io.ktor.client.request.*

class Greeting {
    private val platform: Platform = getPlatform()
    private val httpClient = HttpClient()
    suspend fun greet(): String {
        val resp = httpClient.get("https://ktor.io/docs/")
        return "Hello, ${platform.name}!"
    }
}