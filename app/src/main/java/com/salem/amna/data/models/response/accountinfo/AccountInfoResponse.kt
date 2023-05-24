package com.salem.amna.data.models.response.accountinfo

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.UserModel

data class AccountInfoResponse(

	@field:SerializedName("user")
	val user: UserModel? = null,

)


