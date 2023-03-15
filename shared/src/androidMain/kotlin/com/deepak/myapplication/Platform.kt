package com.deepak.myapplication

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getNetworkClient(): HttpClient {
    return HttpClient(OkHttp){
        engine {

        }
    }
}