package com.af2905.beawareofmovies.data.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.data.repository.database.entity.NowPlayingMovieEntity

@Dao
interface NowPlayingMovieDao {
    @Query("SELECT * FROM now_playing_movies")
    fun getAll(): List<NowPlayingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(nowPlayingMovies: List<NowPlayingMovieEntity>)
}