package com.af2905.beawareofmovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.data.database.entity.UserWatchMovieEntity

@Dao
interface UserWatchMovieDao {
    @Query("SELECT * FROM user_watch_movies")
    fun getAll(): List<UserWatchMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(watchMovie: UserWatchMovieEntity)
}