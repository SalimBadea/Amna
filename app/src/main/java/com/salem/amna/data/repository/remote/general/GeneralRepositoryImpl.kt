package com.salem.amna.data.repository.remote.general

import com.salem.amna.data.apiservice.general.GeneralService
import com.salem.amna.data.models.post_body.ContactUsBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.general.GeneralResponse
import com.salem.amna.data.models.response.pages.PagesResponse
import com.salem.amna.domain.repository.general.GeneralRepository
import retrofit2.Response
import javax.inject.Inject

class GeneralRepositoryImpl @Inject constructor(
    private val api: GeneralService
) : GeneralRepository {
    override suspend fun getAbout(): Response<MainResponseModel<PagesResponse>> = api.getAbout()

    override suspend fun getPrivacy(): Response<MainResponseModel<PagesResponse>> = api.getPrivacy()

    override suspend fun contactUs(contactUsBody: ContactUsBody): Response<MainResponseModel<ContactUsResponse>>
        =  api.contactUs(contactUsBody)


}