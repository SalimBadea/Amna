package net.moltaqa.talab_client.data.repository.remote.auth

import net.moltaqa.talab_client.data.apiservice.auth.AuthService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.RegisterBody
import net.moltaqa.talab_client.data.models.response.auth.RegisterResponse
import net.moltaqa.talab_client.domin.repository.auth.RegisterRepository
import retrofit2.Response
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: AuthService
) : RegisterRepository {

    override suspend fun register(
        registerBody: RegisterBody
    ): Response<MainResponseModel<RegisterResponse?>> {
        return api.register(
            registerBody
        )
    }
}