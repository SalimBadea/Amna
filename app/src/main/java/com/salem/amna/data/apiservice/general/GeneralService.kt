package com.salem.amna.data.apiservice.general

import com.salem.amna.data.models.post_body.ContactUsBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.general.GeneralResponse
import retrofit2.Response
import retrofit2.http.*


interface GeneralService {

    @GET("general/pages/{id}}")
    suspend fun getGeneral(
        @Path("id") page:Int
    ): Response<MainResponseModel<GeneralResponse>>

    @POST("general/contact-us")
    suspend fun contactUs(
        @Body contactUsBody: ContactUsBody
    ): Response<MainResponseModel<ContactUsResponse>>

}