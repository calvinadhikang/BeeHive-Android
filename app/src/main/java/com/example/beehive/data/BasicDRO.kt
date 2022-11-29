package com.example.beehive.data

import com.google.gson.annotations.SerializedName

data class BasicDRO(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
