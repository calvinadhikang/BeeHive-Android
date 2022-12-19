package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ListCategoryDRO(

	@field:SerializedName("data")
	val data: List<Category?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class StingsRelatedItem(

	@field:SerializedName("MAX_REVISION_PREMIUM")
	val MAXREVISIONPREMIUM: Int? = null,

	@field:SerializedName("PRICE_BASIC")
	val pRICEBASIC: Int? = null,

	@field:SerializedName("MAX_REVISION_BASIC")
	val mAXREVISIONBASIC: Int? = null,

	@field:SerializedName("CREATED_AT")
	val cREATEDAT: String? = null,

	@field:SerializedName("DELETED_AT")
	val dELETEDAT: String? = null,

	@field:SerializedName("NAMA_THUMBNAIL")
	val nAMATHUMBNAIL: String? = null,

	@field:SerializedName("ID_STING")
	val iDSTING: String? = null,

	@field:SerializedName("STATUS")
	val sTATUS: Int? = null,

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

	@field:SerializedName("pivot")
	val pivot: Pivot? = null,

	@field:SerializedName("UPDATED_AT")
	val uPDATEDAT: String? = null,

	@field:SerializedName("PRICE_PREMIUM")
	val pRICEPREMIUM: Int? = null
) : Parcelable

@Parcelize
data class Pivot(

	@field:SerializedName("ID_CATEGORY")
	val ID_CATEGORY: Int? = null,

	@field:SerializedName("ID_STING")
	val ID_STING: String? = null
) : Parcelable

@Parcelize
data class Category(

	@field:SerializedName("stingsRelatedCount")
	val StingsRelatedCount: Int? = null,

	@field:SerializedName("NAMA_CATEGORY")
	val NAMA_CATEGORY: String? = null,

	@field:SerializedName("ID_CATEGORY")
	val ID_CATEGORY: Int? = null,

	@field:SerializedName("CREATED_AT")
	val CREATED_AT: String? = null,

	@field:SerializedName("stings_related")
	val stingsRelated: List<StingsRelatedItem?>? = null,

	@field:SerializedName("DELETED_AT")
	val DELETED_AT: String? = null,

	@field:SerializedName("UPDATED_AT")
	val UPDATED_AT: String? = null
) : Parcelable{
	override fun toString(): String {
		return NAMA_CATEGORY!!
	}
}
