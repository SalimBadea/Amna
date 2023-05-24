package com.salem.amna.domain.repository.auth

import com.salem.amna.data.models.post_body.RegisterBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.RegisterResponse
import retrofit2.Response

interface RegisterRepository {

    suspend fun register(registerBody: RegisterBody): Response<MainResponseModel<RegisterResponse?>>
}