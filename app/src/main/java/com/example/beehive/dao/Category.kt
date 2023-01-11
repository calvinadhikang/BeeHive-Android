package com.example.beehive.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.beehive.data.StingsRelatedItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Category")
data class Category (
    @PrimaryKey(autoGenerate = false)
    val ID_CATEGORY: Int,
    val StingsRelatedCount: Int,
    val NAMA_CATEGORY: String,
    val CREATED_AT: String,
    val DELETED_AT: String? = null,
    val UPDATED_AT: String? = null
)