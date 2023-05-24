package com.salem.amna.data.repository.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.salem.amna.data.repository.local.preference.PreferencesKeys.accountType
import com.salem.amna.data.repository.local.preference.PreferencesKeys.fcmTokenKey
import com.salem.amna.data.repository.local.preference.PreferencesKeys.isLang
import com.salem.amna.data.repository.local.preference.PreferencesKeys.isLogin
import com.salem.amna.data.repository.local.preference.PreferencesKeys.langKey
import com.salem.amna.data.repository.local.preference.PreferencesKeys.latPreferencesKey
import com.salem.amna.data.repository.local.preference.PreferencesKeys.longPreferencesKey
import com.salem.amna.data.repository.local.preference.PreferencesKeys.userId
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class LocalePreference @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun setLoginState(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.isLogin] = isLogin
        }
    }
    suspend fun getLoginState(): Boolean? = dataStore.data.first()[isLogin]

    suspend fun setLangState(isLang: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.isLang] = isLang
        }
    }
    suspend fun getLangState(): Boolean? = dataStore.data.first()[isLang]

    suspend fun saveToken(id: String) {
        dataStore.edit { preferences ->
            preferences[userId] = id
        }
    }

    suspend fun getToken(): String? = dataStore.data.first()[userId]


    suspend fun removeToken() {
        dataStore.edit { preferences ->
            preferences[userId] = ""
        }
    }
    suspend fun getFCMToken(): String? = dataStore.data.first()[fcmTokenKey]

    suspend fun setLatLang(lat: String,long: String) {
        dataStore.edit { preferences ->
            preferences[latPreferencesKey] = lat
            preferences[longPreferencesKey] = long
        }
    }
    suspend fun getLat(): String? = dataStore.data.first()[latPreferencesKey]
    suspend fun getLong(): String? = dataStore.data.first()[longPreferencesKey]

    suspend fun removeUserData() {
        dataStore.edit { preferences ->
            preferences[userId] = ""
            preferences[latPreferencesKey] = ""
            preferences[longPreferencesKey] = ""
            preferences[fcmTokenKey] = ""
            preferences[isLogin] = false
        }
    }

    suspend fun saveFCMToken(token: String) {
        dataStore.edit { preferences ->
            preferences[fcmTokenKey] = token
        }
    }

    suspend fun saveLanguage(lang: String) {
        dataStore.edit { preferences ->
            preferences[langKey] = lang
        }
    }

    suspend fun setAccountType(type: Int){
        dataStore.edit { preferences ->
            preferences[accountType] = type
        }
    }

    suspend fun getAccountType(): Int? = dataStore.data.first()[accountType]


    suspend fun getLanguage(): String? = dataStore.data.first()[langKey]

}