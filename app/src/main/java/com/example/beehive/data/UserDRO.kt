package com.example.beehive.api_config

import com.google.gson.annotations.SerializedName

data class UserDRO(

	@field:SerializedName("data")
	val data: Data? = null,

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

data class Data(

	@field:SerializedName("NAMA")
	val NAMA: String? = null,

	@field:SerializedName("CREATED_AT")
	val CREATEDAT: String? = null,

	@field:SerializedName("PICTURE")
	val PICTURE: String? = null,

	@field:SerializedName("EMAIL_VERIFIED_AT")
	val EMAILVERIFIEDAT: String? = null,

	@field:SerializedName("DELETED_AT")
	val DELETEDAT: Any? = null,

	@field:SerializedName("BIO")
	val BIO: String? = null,

	@field:SerializedName("EMAIL")
	val EMAIL: String? = null,

	@field:SerializedName("BALANCE")
	val BALANCE: String? = null,

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
	val TANGGALLAHIR: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATEDAT: String? = null,

	@field:SerializedName("REMEMBER_TOKEN")
	val REMEMBER_TOKEN: String? = null
)