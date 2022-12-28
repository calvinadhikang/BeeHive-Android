package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MostStingDRO(

	@field:SerializedName("data")
	val data: StingMost? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class StingMost(

	@field:SerializedName("MAX_REVISION_PREMIUM")
	val MAX_REVISION_PREMIUM: String? = null,

	@field:SerializedName("PRICE_BASIC")
	val PRICE_BASIC: String? = null,

	@field:SerializedName("MAX_REVISION_BASIC")
	val MAX_REVISION_BASIC: String? = null,

	@field:SerializedName("CREATED_AT")
	val CREATED_AT: String? = null,

	@field:SerializedName("DELETED_AT")
	val DELETED_AT: String? = null,

	@field:SerializedName("author")
	val author: Author? = null,

	@field:SerializedName("NAMA_THUMBNAIL")
	val NAMA_THUMBNAIL: String? = null,

	@field:SerializedName("ID_STING")
	val ID_STING: String? = null,

	@field:SerializedName("STATUS")
	val STATUS: String? = null,

	@field:SerializedName("DESKRIPSI")
	val DESKRIPSI: String? = null,

	@field:SerializedName("DESKRIPSI_BASIC")
	val DESKRIPSI_BASIC: String? = null,

	@field:SerializedName("RATING")
	val RATING: String? = null,

	@field:SerializedName("TITLE_STING")
	val TITLE_STING: String? = null,

	@field:SerializedName("DESKRIPSI_PREMIUM")
	val DESKRIPSI_PREMIUM: String? = null,

	@field:SerializedName("EMAIL_BEEWORKER")
	val EMAIL_BEEWORKER: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATED_AT: String? = null,

	@field:SerializedName("PRICE_PREMIUM")
	val PRICE_PREMIUM: String? = null
) : Parcelable

@Parcelize
data class Author(

	@field:SerializedName("SUBSCRIBED_AT")
	val SUBSCRIBED_AT: String? = null,

	@field:SerializedName("NAMA")
	val NAMA: String? = null,

	@field:SerializedName("CREATED_AT")
	val CREATED_AT: String? = null,

	@field:SerializedName("PICTURE")
	val PICTURE: String? = null,

	@field:SerializedName("EMAIL_VERIFIED_AT")
	val EMAIL_VERIFIED_AT: String? = null,

	@field:SerializedName("DELETED_AT")
	val DELETED_AT: String? = null,

	@field:SerializedName("BIO")
	val BIO: String? = null,

	@field:SerializedName("EMAIL")
	val EMAIL: String? = null,

	@field:SerializedName("BALANCE")
	val BALANCE: String? = null,

	@field:SerializedName("SUBSCRIBED")
	val SUBSCRIBED: String? = null,

	@field:SerializedName("STATUS")
	val STATUS: String? = null,

	@field:SerializedName("PASSWORD")
	val PASSWORD: String? = null,

	@field:SerializedName("RATING")
	val RATING: String? = null,

	@field:SerializedName("TANGGAL_LAHIR")
	val TANGGAL_LAHIR: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATED_AT: String? = null,

	@field:SerializedName("REMEMBER_TOKEN")
	val REMEMBER_TOKEN: String? = null
) : Parcelable
