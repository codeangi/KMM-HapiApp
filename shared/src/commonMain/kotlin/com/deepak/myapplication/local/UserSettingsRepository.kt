package com.deepak.myapplication.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.deepak.myapplication.model.AccessTokenData
import kotlinx.coroutines.flow.first

class UserSettingsRepository(private val dataStore: DataStore<Preferences>) {
    private val userIdKey = stringPreferencesKey("user_id")
    private val patientIdKey = stringPreferencesKey("patient_id")
    private val accessTokenKey = stringPreferencesKey("access_token")

    suspend fun saveUserId(userId: String) {
        storeStringValue(userIdKey, userId)
    }

    suspend fun savePatientId(patientId: String) {
        storeStringValue(patientIdKey, patientId)
    }

    suspend fun saveUserToken(accessToken: AccessTokenData) {
        storeStringValue(accessTokenKey, accessToken.accessToken)
    }

    suspend fun getUserId() = dataStore.data.first()[userIdKey]
    suspend fun getPatientId() = dataStore.data.first()[patientIdKey]

    suspend fun getAccessToken() = dataStore.data.first()[accessTokenKey]
    private suspend fun storeStringValue(key: Preferences.Key<String>, value: String) {
        dataStore.edit {
            it[key] = value
        }
    }
}