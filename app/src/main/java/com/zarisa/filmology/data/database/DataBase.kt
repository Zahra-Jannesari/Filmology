package com.zarisa.filmology.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zarisa.filmology.model.Film
import com.zarisa.filmology.model.UpcomingFilm

@Database(entities = [Film::class, UpcomingFilm::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {
        var INSTANCE: AppDatabase? = null
        fun getAppDataBase(context: Context): AppDatabase {
            val _Instance = INSTANCE
            if (_Instance != null)
                return _Instance
            synchronized(AppDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "myDB"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}