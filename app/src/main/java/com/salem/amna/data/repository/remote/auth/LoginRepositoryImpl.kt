package net.moltaqa.talab_client.data.repository.remote.auth

import net.moltaqa.talab_client.data.apiservice.auth.AuthService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.response.auth.LoginResponse
import net.moltaqa.talab_client.data.models.postbody.LoginBody
import net.moltaqa.talab_client.domin.repository.auth.LoginRepository
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: AuthService
) : LoginRepository {


    override suspend fun login(body: LoginBody): Response<MainResponseModel<LoginResponse>> {
        return api.login(body)
    }
}