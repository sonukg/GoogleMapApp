package com.sonukg.theonepractical.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sonukg.theonepractical.interfaces.PlaceDao
import com.sonukg.theonepractical.model.LocalPlaceModel

@Database(entities = [LocalPlaceModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "one_practical"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}