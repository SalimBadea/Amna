package com.salem.amna.data.apiservice.accountinfo

import com.salem.amna.data.models.post_body.AccountInfoBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.accountinfo.AccountInfoResponse
import retrofit2.Response
import retrofit2.http.*


interface AccountInfoService {

    @POST("profile")
    suspend fun updateAccountInfo(
        @Body accountInfoBody: AccountInfoBody
    ): Response<MainResponseModel<AccountInfoResponse>>

    @GET("profile")
    suspend fun getAccountInfo(): Response<MainResponseModel<AccountInfoResponse>>



}