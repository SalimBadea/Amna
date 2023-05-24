package com.salem.amna.data.repository.remote.auth

import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.domain.repository.auth.LoginRepository
import retrofit2.Response
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: AuthService
) : LoginRepository {


    override suspend fun login(body: LoginBody): Response<MainResponseModel<LoginResponse>> {
        return api.login(body)
    }
}