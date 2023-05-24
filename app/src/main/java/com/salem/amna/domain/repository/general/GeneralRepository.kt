package com.salem.amna.domain.repository.general

import com.salem.amna.data.models.post_body.ContactUsBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.general.GeneralResponse
import retrofit2.Response


interface GeneralRepository {

    suspend fun getGeneralData(page: Int): Response<MainResponseModel<GeneralResponse>>

    suspend fun contactUs(
        contactUsBody: ContactUsBody
    ): Response<MainResponseModel<ContactUsResponse>>



}