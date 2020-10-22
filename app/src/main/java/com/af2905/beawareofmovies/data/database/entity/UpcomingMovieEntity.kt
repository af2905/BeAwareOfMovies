package com.af2905.beawareofmovies.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.af2905.beawareofmovies.Constants.TABLE_NAME_UPCOMING_MOVIES

@Entity(tableName = TABLE_NAME_UPCOMING_MOVIES)
data class UpcomingMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val posterPath: String? = null
)