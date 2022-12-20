package com.example.beehive.api_config

import com.google.gson.annotations.SerializedName

data class UserDRO(

	@field:SerializedName("data")
	val data: UserData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ROLES(

	@field:SerializedName("Status")
	val status: Int? = null,

	@field:SerializedName("Role")
	val role: String? = null
)

data class UserData(

	@field:SerializedName("NAMA")
    var NAMA: String? = null,

	@field:SerializedName("CREATED_AT")
	val CREATED_AT: String? = null,

	@field:SerializedName("PICTURE")
	val PICTURE: String? = null,

	@field:SerializedName("EMAIL_VERIFIED_AT")
	val EMAIL_VERIFIED_AT: String? = null,

	@field:SerializedName("DELETED_AT")
	val DELETED_AT: Any? = null,

	@field:SerializedName("BIO")
	val BIO: String? = null,

	@field:SerializedName("EMAIL")
	val EMAIL: String? = null,

	@field:SerializedName("BALANCE")
	var BALANCE: String? = null,

	@field:SerializedName("SUBSCRIBED")
	val SUBSCRIBED: Int? = null,

	@field:SerializedName("STATUS")
	val STATUS: Int? = null,

	@field:SerializedName("PASSWORD")
	val PASSWORD: String? = null,

	@field:SerializedName("RATING")
	val RATING: String? = null,

	@field:SerializedName("ROLES")
	val ROLES: ROLES? = null,

	@field:SerializedName("TANGGAL_LAHIR")
	val TANGGAL_LAHIR: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATED_AT: String? = null,

	@field:SerializedName("REMEMBER_TOKEN")
	val REMEMBER_TOKEN: String? = null
)
