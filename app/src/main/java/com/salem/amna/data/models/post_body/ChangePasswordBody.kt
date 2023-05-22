package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(

	@field:SerializedName("current_password")
	val current_password: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("password_confirmation")
	val passwordConfirmation: String? = null,


	)

