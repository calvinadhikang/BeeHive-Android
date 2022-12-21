package com.example.beehive.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    User::class
],version=1)
abstract class AppDatabase : RoomDatabase(){
    abstract val userDAO: UserDAO

    companion object {
        private var _database: AppDatabase? = null

        fun build(context: Context?): AppDatabase {
            if(_database == null){
                _database = Room.databaseBuilder(context!!,AppDatabase::class.java,"beehive_database_1").build()
            }
            return _database!!
        }
    }
}