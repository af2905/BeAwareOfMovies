package com.af2905.beawareofmovies.data.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.af2905.beawareofmovies.data.repository.database.entity.UserLikedMovieEntity

@Dao
interface UserLikedMovieDao {
    @Query("SELECT * FROM user_liked_movies")
    fun getAll(): List<UserLikedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(likedMovie: UserLikedMovieEntity)
}