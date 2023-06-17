package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class AddressBody(

	@field:SerializedName("lat")
	val latitude: String? = null,

	@field:SerializedName("lng")
	val longitude: String? = null,

	@field:SerializedName("governorate_id")
	val governorate_id: Int? = null,

	@field:SerializedName("city_id")
	val city_id: Int? = null,

	@field:SerializedName("address")
	val address: String? = null,

	val _method: String? = null,


)
