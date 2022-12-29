package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ListStingDRO(

	@field:SerializedName("data")
	val data: List<StingData?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class StingDRO(

	@field:SerializedName("data")
	val data: StingData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class StingData(

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
