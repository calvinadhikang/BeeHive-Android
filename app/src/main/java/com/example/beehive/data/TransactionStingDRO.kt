package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ListTransactionStingDRO(

	@field:SerializedName("data")
	val data: List<TransactionStingData?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class TransactionStingDRO(

	@field:SerializedName("data")
	val data: TransactionStingData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class TransactionStingData(

	@field:SerializedName("IS_PREMIUM")
	val IS_PREMIUM: String? = null,

	@field:SerializedName("COMMISION")
	val COMMISION: String? = null,

	@field:SerializedName("CREATED_AT")
	val CREATED_AT: String? = null,

	@field:SerializedName("RATE")
	val RATE: String? = null,

	@field:SerializedName("TAX")
	val TAX: String? = null,

	@field:SerializedName("sting")
	val sting: StingData? = null,

	@field:SerializedName("EMAIL_FARMER")
	val EMAIL_FARMER: String? = null,

	@field:SerializedName("REQUIREMENT_PROJECT")
	val REQUIREMENT_PROJECT: String? = null,

	@field:SerializedName("ID_STING")
	val ID_STING: String? = null,

	@field:SerializedName("revisionWaiting")
	val revisionWaiting: Int? = null,

	@field:SerializedName("revisionLeft")
	val revisionLeft: Int? = null,

	@field:SerializedName("STATUS")
	val STATUS: String? = null,

	@field:SerializedName("JUMLAH_REVISI")
	val JUMLAH_REVISI: String? = null,

	@field:SerializedName("complains")
	val complains: List<ComplainDRO?>? = null,

	@field:SerializedName("ID_TRANSACTION")
	val ID_TRANSACTION: String? = null,

	@field:SerializedName("statusString")
	val statusString: String? = null,

	@field:SerializedName("DATE_START")
	val DATE_START: String? = null,

	@field:SerializedName("REVIEW")
	val REVIEW: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATED_AT: String? = null,

	@field:SerializedName("FILENAME_FINAL")
	val FILENAME_FINAL: String? = null,

	@field:SerializedName("DATE_END")
	val DATE_END: String? = null
) : Parcelable


