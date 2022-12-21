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
	var CREATED_AT: String? = null,

	@field:SerializedName("PICTURE")
	var PICTURE: String? = null,

	@field:SerializedName("EMAIL_VERIFIED_AT")
	var EMAIL_VERIFIED_AT: String? = null,

	@field:SerializedName("DELETED_AT")
	var DELETED_AT: Any? = null,

	@field:SerializedName("BIO")
	var BIO: String? = null,

	@field:SerializedName("EMAIL")
	val EMAIL: String? = null,

	@field:SerializedName("BALANCE")
	var BALANCE: String? = null,

	@field:SerializedName("SUBSCRIBED")
	var SUBSCRIBED: Int? = null,

	@field:SerializedName("STATUS")
	var STATUS: Int? = null,

	@field:SerializedName("PASSWORD")
	var PASSWORD: String? = null,

	@field:SerializedName("RATING")
	var RATING: String? = null,

	@field:SerializedName("ROLES")
	var ROLES: ROLES? = null,

	@field:SerializedName("TANGGAL_LAHIR")
	var TANGGAL_LAHIR: String? = null,

	@field:SerializedName("UPDATED_AT")
	var UPDATED_AT: String? = null,

	@field:SerializedName("REMEMBER_TOKEN")
	var REMEMBER_TOKEN: String? = null
)
