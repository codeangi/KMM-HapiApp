package com.deepak.myapplication.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class DataStoreProvider(private val context:Context) {
    actual fun createDataStore(): DataStore<Preferences> {
       return getDataStore { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
    }
}