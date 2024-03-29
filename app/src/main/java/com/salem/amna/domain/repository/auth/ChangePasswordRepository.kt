package com.salem.amna.domain.repository.auth

import com.salem.amna.data.models.post_body.ChangePasswordBody
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import retrofit2.Response


interface ChangePasswordRepository {

    suspend fun changePassword(body: ChangePasswordBody): Response<MainResponseModel<ContactUsResponse>>

}