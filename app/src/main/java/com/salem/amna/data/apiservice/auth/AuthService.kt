package com.salem.amna.data.apiservice.auth

import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.post_body.RegisterBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.data.models.response.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body


interface AuthService {

    @POST("register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): Response<MainResponseModel<RegisterResponse?>>

    @POST("login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): Response<MainResponseModel<LoginResponse>>

    @POST("verifiy")
    suspend fun verify(
        @Query("code") code: String
    ): Response<MainResponseModel<Any>>


}