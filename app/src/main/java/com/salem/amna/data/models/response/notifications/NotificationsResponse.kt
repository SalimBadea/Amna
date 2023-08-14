package com.salem.amna.data.models.response.notifications

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.NotificationsModel

data class NotificationsResponse(

    @SerializedName("read_notifications")
    val readNotifications: MutableList<NotificationsModel>? = null,

    @SerializedName("unread_notifications")
    val unreadNotifications: MutableList<NotificationsModel>? = null,
)
