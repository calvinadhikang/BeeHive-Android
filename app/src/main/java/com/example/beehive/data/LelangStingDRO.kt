package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ListLelangStingDRO(

	@field:SerializedName("data")
	val data: List<LelangStingData?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
@Parcelize
data class LelangStingDRO(

	@field:SerializedName("data")
	val data: LelangStingData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class LelangStingData(

	@field:SerializedName("COMMISION")
	val COMMISION: String? = null,

	@field:SerializedName("CREATED_AT")
	val CREATED_AT: String? = null,

	@field:SerializedName("RATE")
	val RATE: String? = null,

	@field:SerializedName("TAX")
	val TAX: String? = null,

	@field:SerializedName("EMAIL_FARMER")
	val EMAIL_FARMER: String? = null,

	@field:SerializedName("REQUIREMENT_PROJECT")
	val REQUIREMENT_PROJECT: String? = null,

	@field:SerializedName("STATUS")
	val STATUS: String? = null,

	@field:SerializedName("TITLE_STING")
	val TITLE_STING: String? = null,

	@field:SerializedName("EMAIL_BEEWORKER")
	val EMAIL_BEEWORKER: String? = null,

	@field:SerializedName("ID_LELANG_STING")
	val ID_LELANG_STING: String? = null,

	@field:SerializedName("REVIEW")
	val REVIEW: String? = null,

	@field:SerializedName("DATE_START")
	val DATE_START: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATED_AT: String? = null,

	@field:SerializedName("FILENAME_FINAL")
	val FILENAME_FINAL: String? = null,

	@field:SerializedName("DATE_END")
	val DATE_END: String? = null
) : Parcelable
