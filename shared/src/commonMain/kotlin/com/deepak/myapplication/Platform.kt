package com.deepak.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
