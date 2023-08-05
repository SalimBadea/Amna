package com.salem.amna.domain.repository

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.points.BanksResponse
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import retrofit2.Response

interface PointsRepository {

    suspend fun getPoints(): Response<MainResponseModel<PointsResponse>>
    suspend fun getWithdrawals(type: String?): Response<MainResponseModel<WithdrawalsResponse>>
    suspend fun getBanks(): Response<MainResponseModel<BanksResponse>>
}