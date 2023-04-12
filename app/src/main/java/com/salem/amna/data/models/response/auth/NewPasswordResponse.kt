package com.salem.amna.data.models.response.auth

import com.google.gson.annotations.SerializedName

data class NewPasswordResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("cart_items_count")
	val cart_items_count: Int? = null,
)
