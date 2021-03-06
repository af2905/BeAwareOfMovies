package com.af2905.beawareofmovies.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val popularity: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("video")
    val isVideo: Boolean? = null,
    val id: Int,
    @SerializedName("adult")
    val isAdult: Boolean? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    val title: String? = null,
    val overview: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String? = null
)