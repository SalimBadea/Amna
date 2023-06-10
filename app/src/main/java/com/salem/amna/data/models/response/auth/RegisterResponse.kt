package com.salem.amna.data.models.response.auth

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.UserModel

data class RegisterResponse(

	@field:SerializedName("user")
	val user: UserModel? = null,

	@SerializedName("token")
	val token: String? = null,

)



