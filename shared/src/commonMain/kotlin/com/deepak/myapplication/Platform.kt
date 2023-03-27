package com.deepak.myapplication

import io.ktor.client.*
import org.koin.core.module.Module


expect fun platformModule(): Module
expect fun getNetworkClient(): HttpClient