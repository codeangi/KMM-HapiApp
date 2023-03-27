package com.deepak.myapplication.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import platform.Foundation.*
import platform.posix.connectx

actual class DataStoreProvider {
    actual fun createDataStore(): DataStore<Preferences> {
       return getDataStore(producePath = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDownloadsDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            requireNotNull(documentDirectory).path+"/$DATA_STORE_FILE_NAME"
        })
    }
}