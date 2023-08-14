package com.salem.amna.data.apiservice

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.notifications.NotificationsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NotificationsService {
    @GET("notifications")
    suspend fun getNotifications(): Response<MainResponseModel<NotificationsResponse>>
}