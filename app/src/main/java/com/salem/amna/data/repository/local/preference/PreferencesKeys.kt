package com.salem.amna.data.repository.local.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val userId = stringPreferencesKey("user_id")
    val userApiToken = stringPreferencesKey("USER_API_TOKEN")
    val accountType = intPreferencesKey("ACCOUNT_TYPE")

    val isLogin = booleanPreferencesKey("is_login")
    val isFirstTime = booleanPreferencesKey("is_first_time")
    val isLang = booleanPreferencesKey("is_lang")
    val latPreferencesKey = stringPreferencesKey("lat")
    val longPreferencesKey = stringPreferencesKey("long")
    val fcmTokenKey = stringPreferencesKey("FCM_TOKEN")
    val langKey = stringPreferencesKey("LANGUAGE")

}