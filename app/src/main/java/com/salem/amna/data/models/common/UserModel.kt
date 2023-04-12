package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class UserModel(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_verified_at")
	val phoneVerifiedAt: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("cart_uuid")
	val cartUuid: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("unique_id")
	val uniqueId: String? = null,

	@field:SerializedName("cart_items_count")
	val cart_items_count: Int? = null,

	)


