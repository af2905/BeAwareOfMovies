package com.af2905.beawareofmovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.Constants.TABLE_NAME_MOVIES
import com.af2905.beawareofmovies.data.database.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM $TABLE_NAME_MOVIES WHERE category = :category")
    fun getAll(category: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)
}