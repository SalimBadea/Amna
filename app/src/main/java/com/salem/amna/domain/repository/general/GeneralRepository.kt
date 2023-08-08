package com.salem.amna.domain.repository.general

import com.salem.amna.data.models.post_body.ContactUsBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.general.GeneralResponse
import com.salem.amna.data.models.response.pages.PagesResponse
import retrofit2.Response


interface GeneralRepository {

    suspend fun getAbout(): Response<MainResponseModel<PagesResponse>>

    suspend fun getPrivacy(): Response<MainResponseModel<PagesResponse>>

    suspend fun contactUs(
        contactUsBody: ContactUsBody
    ): Response<MainResponseModel<ContactUsResponse>>



}