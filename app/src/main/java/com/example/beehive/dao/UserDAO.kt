package com.example.beehive.dao

import androidx.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun get():User //user remember me hanya 1


    @Query("DELETE FROM User")
    fun logout()

    @Insert
    suspend fun insert(U:User)
    @Update
    suspend fun update(U:User)
    @Delete
    suspend fun delete(U:User)
}