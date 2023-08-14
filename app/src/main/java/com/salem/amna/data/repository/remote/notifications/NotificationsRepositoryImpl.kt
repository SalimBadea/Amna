package com.salem.amna.data.repository.remote.notifications

import com.salem.amna.data.apiservice.NotificationsService
import com.salem.amna.data.apiservice.addresses.AddressesServices
import com.salem.amna.data.models.post_body.AddressBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.notifications.NotificationsResponse
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.domain.repository.notifications.NotificationsRepository
import retrofit2.Response
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    val api: NotificationsService
): NotificationsRepository {

    override suspend fun getNotifications(): Response<MainResponseModel<NotificationsResponse>> =
        api.getNotifications()

}