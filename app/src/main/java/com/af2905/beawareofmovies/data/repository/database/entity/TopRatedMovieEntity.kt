package com.af2905.beawareofmovies.data.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated_movies")
data class TopRatedMovieEntity(
    val popularity: Double? = null,
    val voteCount: Int? = null,
    val isVideo: Boolean? = null,
    @PrimaryKey
    val id: Int,
    val isAdult: Boolean? = null,
    val backdropPath: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val genreIds: List<Int>? = null,
    val title: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val posterPath: String? = null
)