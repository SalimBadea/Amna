package com.salem.amna.data.models.response.auth

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.UserModel

data class VerificationCodeResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("user")
	val user: UserModel? = null,

	@field:SerializedName("cart_items_count")
	val cart_items_count: Int? = null,
)

