package com.example.pregnancyvitalstracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PregnancyEntity::class], version = 1, exportSchema = false)
abstract class PregnancyDatabase : RoomDatabase(){

    abstract fun pregnancyDao() : PregnancyDao
    companion object {
        @Volatile
        private var Instance: PregnancyDatabase? = null

        fun getDatabase(context: Context): PregnancyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PregnancyDatabase::class.java, "pregnancy_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}