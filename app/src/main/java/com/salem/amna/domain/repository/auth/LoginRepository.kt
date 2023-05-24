package com.salem.amna.domain.repository.auth

import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import retrofit2.Response


interface LoginRepository {

    suspend fun login(body: LoginBody): Response<MainResponseModel<LoginResponse>>

}