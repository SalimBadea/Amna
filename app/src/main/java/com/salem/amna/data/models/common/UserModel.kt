package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("account_type")
	val account_type: String? = null,

	@field:SerializedName("image")
	val image: String? = null,


	)


