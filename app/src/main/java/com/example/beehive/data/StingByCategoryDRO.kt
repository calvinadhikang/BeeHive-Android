package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class StingByCategoryDRO(

	@field:SerializedName("data")
	val data: List<StingMost?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("MAX_REVISION_PREMIUM")
	val mAXREVISIONPREMIUM: String? = null,

	@field:SerializedName("PRICE_BASIC")
	val pRICEBASIC: String? = null,

	@field:SerializedName("MAX_REVISION_BASIC")
	val mAXREVISIONBASIC: String? = null,

	@field:SerializedName("CREATED_AT")
	val cREATEDAT: String? = null,

	@field:SerializedName("DELETED_AT")
	val dELETEDAT: String? = null,

	@field:SerializedName("NAMA_THUMBNAIL")
	val nAMATHUMBNAIL: String? = null,

	@field:SerializedName("ID_STING")
	val iDSTING: String? = null,

	@field:SerializedName("STATUS")
	val sTATUS: String? = null,

	@field:SerializedName("DESKRIPSI")
	val dESKRIPSI: String? = null,

	@field:SerializedName("DESKRIPSI_BASIC")
	val dESKRIPSIBASIC: String? = null,

	@field:SerializedName("RATING")
	val rATING: String? = null,

	@field:SerializedName("TITLE_STING")
	val tITLESTING: String? = null,

	@field:SerializedName("DESKRIPSI_PREMIUM")
	val dESKRIPSIPREMIUM: String? = null,

	@field:SerializedName("EMAIL_BEEWORKER")
	val eMAILBEEWORKER: String? = null,

	@field:SerializedName("PICTURE_BEEWORKER")
	val pICTUREBEEWORKER: String? = null,

	@field:SerializedName("UPDATED_AT")
	val uPDATEDAT: String? = null,

	@field:SerializedName("NAMA_BEEWORKER")
	val nAMABEEWORKER: String? = null,

	@field:SerializedName("JUMLAH_ORDER")
	val jUMLAHORDER: String? = null,

	@field:SerializedName("PRICE_PREMIUM")
	val pRICEPREMIUM: String? = null
) : Parcelable
