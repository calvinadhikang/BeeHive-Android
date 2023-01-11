package com.example.beehive.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.beehive.api_config.ROLES
import com.example.beehive.data.StingData
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = false)
    var REMEMBER_TOKEN:String,
    var EMAIL:String,
    var NAMA:String,
    var STATUS:String,
    var TANGGAL_LAHIR:String,
    var BALANCE:String,
    var BIO:String,
    var RATING:String,
    var PICTURE:String,
    var SUBSCRIBED:String,
    var EMAIL_VERIFIED_AT:String,
    var CREATED_AT:String,
    var UPDATED_AT:String,
) {
}
@Entity(tableName = "Beeworker")
data class Beeworker(
    @PrimaryKey(autoGenerate = false)
    var REMEMBER_TOKEN:String,
    var EMAIL:String,
    var NAMA:String,
    var PICTURE: String? = null,
    var BIO: String? = null,
    var BALANCE: String? = null,
    var RATING: String? = null,
    var TANGGAL_LAHIR: String? = null,
    var jumlahSting: String? = null,
    var jumlahOrderDone: String? = null,
) {
}