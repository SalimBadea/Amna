package com.salem.amna.data.repository.remote.auth

import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.models.post_body.ChangePasswordBody
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.auth.ChangePasswordRepository
import com.salem.amna.domain.repository.auth.LoginRepository
import retrofit2.Response
import javax.inject.Inject

class ChangePasswordRepositoryImpl @Inject constructor(
    private val api: AuthService
) : ChangePasswordRepository {

    override suspend fun changePassword(body: ChangePasswordBody): Response<MainResponseModel<ContactUsResponse>> {
        return api.changePassword(body)
    }
}