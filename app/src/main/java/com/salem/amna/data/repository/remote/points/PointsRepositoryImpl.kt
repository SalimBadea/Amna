package com.salem.amna.data.repository.remote.points

import com.salem.amna.data.apiservice.points.PointsServices
import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.post_body.WithdrawalToWalletBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.points.BanksResponse
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import com.salem.amna.domain.repository.points.PointsRepository
import retrofit2.Response
import javax.inject.Inject

class PointsRepositoryImpl @Inject constructor(
    private val api : PointsServices
): PointsRepository {
    override suspend fun getPoints(): Response<MainResponseModel<PointsResponse>> =
        api.getPoints()

    override suspend fun getWithdrawals(type: String?): Response<MainResponseModel<WithdrawalsResponse>> =
        api.getWithdrawals(type)

    override suspend fun getBanks(): Response<MainResponseModel<BanksResponse>> =
        api.getBanks()

    override suspend fun withdrawalToBankAccount(body: WithdrawalToBankAccountBody): Response<MainResponseModel<ContactUsResponse>> =
        api.withdrawalToBankAccount(body)

    override suspend fun withdrawalToBankCard(body: WithdrawalToBankCardBody): Response<MainResponseModel<ContactUsResponse>> =
        api.withdrawalToBankCard(body)

    override suspend fun withdrawalToWallet(body: WithdrawalToWalletBody): Response<MainResponseModel<ContactUsResponse>> =
        api.withdrawalToWallet(body)
}