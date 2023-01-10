package com.example.beehive.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

@Parcelize
data class ConvertionRatesDRO(

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("documentation")
    val documentation: String? = null,

    @field:SerializedName("terms_of_use")
    val terms_of_use: String? = null,

    @field:SerializedName("time_last_update_unix")
    val time_last_update_unix: String? = null,

    @field:SerializedName("time_last_update_utc")
    val time_last_update_utc: String? = null,

    @field:SerializedName("time_next_update_unix")
    val time_next_update_unix: String? = null,

    @field:SerializedName("time_next_update_utc")
    val time_next_update_utc: String? = null,

    @field:SerializedName("base_code")
    val base_code: String? = null,

    @field:SerializedName("conversion_rates")
    val conversion_rates: String? = null
) : Parcelable