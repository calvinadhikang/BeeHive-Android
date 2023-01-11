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
@Dao
interface CategoryDAO {
    @Query("SELECT * FROM Category")
    fun fetch():List<Category>


    @Query("DELETE FROM Category")
    fun clear()

    @Insert
    suspend fun insert(C:Category)
    @Update
    suspend fun update(C:Category)
    @Delete
    suspend fun delete(C:Category)
}
@Dao
interface BeeworkerDAO {
    @Query("SELECT * FROM Beeworker")
    fun fetch():List<Beeworker>


    @Query("DELETE FROM Beeworker")
    fun clear()

    @Insert
    suspend fun insert(B:Beeworker)
    @Update
    suspend fun update(B:Beeworker)
    @Delete
    suspend fun delete(B:Beeworker)
}