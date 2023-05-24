package com.salem.amna.data.repository.remote.accountinfo

import com.salem.amna.data.apiservice.accountinfo.AccountInfoService
import com.salem.amna.data.models.post_body.AccountInfoBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.accountinfo.AccountInfoResponse
import com.salem.amna.domain.repository.accountinfo.AccountInfoRepository
import retrofit2.Response
import javax.inject.Inject

class AccountInfoRepositoryImpl @Inject constructor(
    private val api: AccountInfoService
) : AccountInfoRepository {


    override suspend fun updateAccountInfo(accountInfoBody: AccountInfoBody): Response<MainResponseModel<AccountInfoResponse>> {
        return api.updateAccountInfo(accountInfoBody)
    }

    override suspend fun getAccountInfo(): Response<MainResponseModel<AccountInfoResponse>>
       = api.getAccountInfo()


}