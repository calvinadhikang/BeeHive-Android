package com.example.beehive.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = false)
    var REMEMBER_TOKEN:String,
    var EMAIL:String,
    var NAMA:String,
) {
}