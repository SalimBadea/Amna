package com.salem.amna.data.repository.remote.auth

import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.domain.repository.auth.LoginRepository
import com.salem.amna.domain.repository.auth.VerifyRepository
import retrofit2.Response
import javax.inject.Inject

class VerifyRepositoryImpl @Inject constructor(
    private val api: AuthService
) : VerifyRepository {

    override suspend fun verify(code: String): Response<MainResponseModel<Any>> =
        api.verify(code)

}