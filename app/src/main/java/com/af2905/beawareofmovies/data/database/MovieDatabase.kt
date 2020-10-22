package com.af2905.beawareofmovies.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.af2905.beawareofmovies.data.database.dao.*
import com.af2905.beawareofmovies.data.database.entity.*

private const val DATABASE_NAME = "MovieDatabase"

@Database(
    entities = [
        NowPlayingMovieEntity::class,
        PopularMovieEntity::class,
        TopRatedMovieEntity::class,
        UpcomingMovieEntity::class,
        UserLikedMovieEntity::class,
        UserWatchMovieEntity::class],
    version = 1, exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun nowPlayingMovieDao(): NowPlayingMovieDao
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun topRatedMovieDao(): TopRatedMovieDao
    abstract fun upcomingMovieDao(): UpcomingMovieDao
    abstract fun userLikedMovieDao(): UserLikedMovieDao
    abstract fun userWatchMovieDao(): UserWatchMovieDao

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