package com.example.beehive.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListComplainDRO(
    @field:SerializedName("data")
    val data: List<ComplainData?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable

@Parcelize
data class ComplainDRO(
    @field:SerializedName("data")
    val data: ComplainData? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable


@kotlinx.parcelize.Parcelize
data class ComplainData(
    @field:SerializedName("COMPLAIN")
    val COMPLAIN: String? = null,

    @field:SerializedName("FILE_REVISION")
    val FILE_REVISION: String? = null,

    @field:SerializedName("ID_COMPLAIN")
    val ID_COMPLAIN: Int? = null,

    @field:SerializedName("CREATED_AT")
    val CREATED_AT: String? = null,

    @field:SerializedName("ID_TRANSACTION")
    val ID_TRANSACTION: String? = null,

    @field:SerializedName("UPDATED_AT")
    val UPDATED_AT: String? = null,

    @field:SerializedName("TYPE")
    val TYPE: String? = null
) : Parcelable