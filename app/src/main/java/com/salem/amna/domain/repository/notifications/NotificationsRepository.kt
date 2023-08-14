package com.salem.amna.domain.repository.notifications

import com.salem.amna.data.models.post_body.AddressBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.notifications.NotificationsResponse
import retrofit2.Response

interface NotificationsRepository {

    suspend fun getNotifications(): Response<MainResponseModel<NotificationsResponse>>

}