package com.af2905.beawareofmovies.data.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.data.repository.database.entity.PopularMovieEntity

@Dao
interface PopularMovieDao {
    @Query("SELECT * FROM popular_movies")
    fun getAll(): List<PopularMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(popularMovies: List<PopularMovieEntity>)
}