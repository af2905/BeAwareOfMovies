package com.af2905.beawareofmovies.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.af2905.beawareofmovies.data.database.dao.MovieDao
import com.af2905.beawareofmovies.data.database.entity.MovieEntity

private const val DATABASE_NAME = "MovieDatabase"

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: MovieDatabase? = null

        @Synchronized
        fun get(context: Context): MovieDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, DATABASE_NAME
                ).build()
            }
            return instance!!
        }
    }
}