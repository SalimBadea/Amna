package net.moltaqa.talab_client.data.repository.remote.auth

import net.moltaqa.talab_client.data.apiservice.auth.AuthService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.response.auth.LoginResponse
import net.moltaqa.talab_client.data.models.response.auth.VerificationCodeResponse
import net.moltaqa.talab_client.data.models.postbody.LoginBody
import net.moltaqa.talab_client.data.models.postbody.SubmitRegisterBody
import net.moltaqa.talab_client.domin.repository.auth.LoginRepository
import net.moltaqa.talab_client.domin.repository.auth.SubmitRegisterRepository
import retrofit2.Response
import javax.inject.Inject

class SubmitRegisterRepositoryImpl @Inject constructor(
    private val api: AuthService
) : SubmitRegisterRepository {
    override suspend fun submitRegister(
        body: SubmitRegisterBody
    ): Response<MainResponseModel<VerificationCodeResponse>> {
        return api.submitRegister(body)
    }


}