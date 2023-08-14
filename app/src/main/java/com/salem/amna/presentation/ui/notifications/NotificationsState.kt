package com.salem.amna.presentation.ui.notifications

import com.salem.amna.data.models.response.notifications.NotificationsResponse

data class NotificationsState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val result: NotificationsResponse? = null,
    val error: String = "",

    )
