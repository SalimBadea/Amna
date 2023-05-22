package com.salem.amna.data.models.response.auth

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.UserModel

data class LoginResponse(

	@field:SerializedName("user")
	val user: UserModel? = null,

	@field:SerializedName("token")
	val accessToken: String? = null,
)


