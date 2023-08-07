package com.salem.amna.domain.repository.points

import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.post_body.WithdrawalToWalletBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.points.BanksResponse
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import retrofit2.Response

interface PointsRepository {

    suspend fun getPoints(): Response<MainResponseModel<PointsResponse>>
    suspend fun getWithdrawals(type: String?): Response<MainResponseModel<WithdrawalsResponse>>
    suspend fun getBanks(): Response<MainResponseModel<BanksResponse>>
    suspend fun withdrawalToBankAccount(body: WithdrawalToBankAccountBody): Response<MainResponseModel<ContactUsResponse>>
    suspend fun withdrawalToBankCard(body: WithdrawalToBankCardBody): Response<MainResponseModel<ContactUsResponse>>
    suspend fun withdrawalToWallet(body: WithdrawalToWalletBody): Response<MainResponseModel<ContactUsResponse>>
}