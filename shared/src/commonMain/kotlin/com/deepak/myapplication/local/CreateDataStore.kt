package com.deepak.myapplication.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import okio.Path.Companion.toPath

private lateinit var dataStore : DataStore<Preferences>
private val lock = SynchronizedObject()

fun getDataStore(producePath:()->String): DataStore<Preferences> =
    synchronized(lock){
        if(::dataStore.isInitialized) dataStore else PreferenceDataStoreFactory.createWithPath(produceFile = {producePath().toPath()}).also {
            dataStore = it
        }
    }
internal const val DATA_STORE_FILE_NAME = "hapi_care_user.preferences_pb"

expect class DataStoreProvider{
    fun createDataStore(): DataStore<Preferences>
}