package com.salem.amna.domain.repository.accountinfo

import com.salem.amna.data.models.post_body.AccountInfoBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.accountinfo.AccountInfoResponse
import retrofit2.Response


interface AccountInfoRepository {

    suspend fun updateAccountInfo(accountInfoBody: AccountInfoBody): Response<MainResponseModel<AccountInfoResponse>>

    suspend fun getAccountInfo(): Response<MainResponseModel<AccountInfoResponse>>

}