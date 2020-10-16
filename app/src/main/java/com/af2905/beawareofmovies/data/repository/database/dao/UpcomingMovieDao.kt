package com.af2905.beawareofmovies.data.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.data.repository.database.entity.UpcomingMovieEntity

@Dao
interface UpcomingMovieDao {
    @Query("SELECT * FROM upcoming_movies")
    fun getAll(): List<UpcomingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(upcomingMovies: List<UpcomingMovieEntity>)
}