package com.salem.amna.data.apiservice.points

import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.post_body.WithdrawalToWalletBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.points.BanksResponse
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.Withdrawals
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PointsServices {

    @GET("points")
    suspend fun getPoints(): Response<MainResponseModel<PointsResponse>>

    @GET("withdrawals")
    suspend fun getWithdrawals(@Query("type") type: String?): Response<MainResponseModel<WithdrawalsResponse>>

    @GET("banks")
    suspend fun getBanks(): Response<MainResponseModel<BanksResponse>>

    @POST("withdrawal_to_bank_account")
    suspend fun withdrawalToBankAccount(@Body body: WithdrawalToBankAccountBody): Response<MainResponseModel<ContactUsResponse>>

    @POST("withdrawal_to_bank_card")
    suspend fun withdrawalToBankCard(@Body body: WithdrawalToBankCardBody): Response<MainResponseModel<ContactUsResponse>>

    @POST("withdrawal_to_ewllaet")
    suspend fun withdrawalToWallet(@Body body: WithdrawalToWalletBody): Response<MainResponseModel<ContactUsResponse>>
}