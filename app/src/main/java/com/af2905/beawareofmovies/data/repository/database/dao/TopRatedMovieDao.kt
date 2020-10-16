package com.af2905.beawareofmovies.data.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.data.repository.database.entity.TopRatedMovieEntity

@Dao
interface TopRatedMovieDao {
    @Query("SELECT * FROM top_rated_movies")
    fun getAll(): List<TopRatedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(topRatedMovies: List<TopRatedMovieEntity>)
}