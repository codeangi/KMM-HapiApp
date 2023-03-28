package com.deepak.myapplication

import com.deepak.myapplication.local.DataStoreProvider
import com.deepak.myapplication.local.DatabaseDriverFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseDriverFactory() }
    single { DataStoreProvider() }
}
