package com.salem.amna.data.apiservice.points

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.points.BanksResponse
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.Withdrawals
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsServices {

    @GET("points")
    suspend fun getPoints(): Response<MainResponseModel<PointsResponse>>

    @GET("withdrawals")
    suspend fun getWithdrawals(@Query("type") type: String?): Response<MainResponseModel<WithdrawalsResponse>>

    @GET("banks")
    suspend fun getBanks(): Response<MainResponseModel<BanksResponse>>
}