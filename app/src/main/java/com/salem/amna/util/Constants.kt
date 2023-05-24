package com.salem.amna.util

import com.salem.amna.BuildConfig
import com.salem.amna.R

typealias ResString = R.string

object Constants {
    const val package_name = BuildConfig.APPLICATION_ID
    const val ENGLISH = "en"
    const val ARABIC = "ar"

    const val PREFERENCES_DB: String = "PreferencesDb"


    // api links part
//const val BASE_URL = "https://safriapp.com/"
    const val LANGUAGE_HEADER_KEY = "Accept-Language"
    const val TOKEN_HEADER_KEY = "Authorization"
    const val USER_TYPE_CODE = 1

    // shared preference part
    const val SHARED_PREFERENCE_NAME = "$package_name sharedPreferenceName"
    const val APP_LANGUAGE = "APP_LANGUAGE"
    const val USER_TOKEN = "USER_TOKEN"
    const val USER_FULL_DATA = "USER_FULL_DATA"
    const val Is_USER_NEED_TO_LOGIN = "Is_USER_NEED_TO_LOGIN"
    const val USER_TYPE = "USER_TYPE"
    const val USER_LAT_LNG = "USER_LAT_LNG"
    const val FULL_LOCATION = "FULL_LOCATION"
    const val VALID_MOBILE_LENGTH = 11

    // Intent Keys
    const val USER_MODEL = "USER_MODEL"
    const val USER_MOBILE = "USER_MOBILE"
    const val CONFIRM_CODE = "CONFIRM_CODE"

    // Address Kinds
    const val ADDRESS_HOME: String = "home"
    const val ADDRESS_WORK: String = "work"
    const val ADDRESS_FRIEND: String = "friend"
    const val ADDRESS_REST: String = "rest"

    const val LATITUDE: String = "latitude"
    const val LONGITUDE: String = "longitude"

    // ORDER KINDS
    const val DELIVERY_KIND = 3
    const val TAKE_AWAY_KIND = 2
    const val BOOKING_TABLE_KIND = 1

    // ORDER STATUS
    const val ORDER_CANCELED = 1
    const val ORDER_DELIVERED = 2

    // TABLE DETAILS
    const val TABLE_INDOOR = 1
    const val TABLE_OUTDOOR = 2
    const val SITTING_OPEN = 1
    const val SITTING_CLOSE = 2

    // PAGE TYPES
    const val PAGE_TERMS = "terms-and-conditions"
    const val PAGE_ABOUT = "about-us"
    const val PAGE_PRIVACY = "privacy-policy"

    const val USER: String = "user"

    // ================== Pusher setup ================
    const val PUSHER_API_KEY: String = "eb4ecf3982abed67d382"
    const val PUSHER_APP_ID: String = "1405657"
    const val PUSHER_CLUSTER: String = "mt1"
    const val PUSHER_SECRET: String = "95a3187f9fed24f77564"
//const val PUSHER_BASE_URL = "https://talab-dev.moltaqadev.com/"

    // ================== Pusher Key ================
    const val PUSHER_CHAT_CHANNELS: String = "chat-channel-"

    // ================== Pusher Events ================
    const val PUSHER_EVENT_CHAT: String = "chat-event-"
}