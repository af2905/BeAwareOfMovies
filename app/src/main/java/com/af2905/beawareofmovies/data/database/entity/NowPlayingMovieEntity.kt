package com.af2905.beawareofmovies.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_movies")
data class NowPlayingMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val posterPath: String? = null
)