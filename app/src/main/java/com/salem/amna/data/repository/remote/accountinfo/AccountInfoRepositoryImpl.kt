package net.moltaqa.talab_client.data.repository.remote.accountinfo

import net.moltaqa.talab_client.data.apiservice.accountinfo.AccountInfoService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.AccountInfoBody
import net.moltaqa.talab_client.data.models.response.accountinfo.AccountInfoResponse
import net.moltaqa.talab_client.data.models.response.auth.LoginResponse
import net.moltaqa.talab_client.domin.repository.accountinfo.AccountInfoRepository
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