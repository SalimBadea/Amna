package com.salem.amna.data.models.response.general

import com.google.gson.annotations.SerializedName

data class GeneralResponse(

	@field:SerializedName("social")
	val social: Social? = null,

	@field:SerializedName("page")
	val page: Page? = null,

)


data class Page(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)


data class Social(

	@field:SerializedName("whatsapp")
	val whatsapp: String? = null,

	@field:SerializedName("twitter")
	val twitter: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("instagram")
	val instagram: String? = null,

	@field:SerializedName("facebook")
	val facebook: String? = null
)
