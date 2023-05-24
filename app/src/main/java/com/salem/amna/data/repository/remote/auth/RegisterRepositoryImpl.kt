package com.salem.amna.data.repository.remote.auth

import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.models.post_body.RegisterBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.RegisterResponse
import com.salem.amna.domain.repository.auth.RegisterRepository
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