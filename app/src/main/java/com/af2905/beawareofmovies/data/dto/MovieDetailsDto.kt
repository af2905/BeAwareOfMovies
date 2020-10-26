package com.af2905.beawareofmovies.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    val adult: Boolean? = null,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any? = null,
    val budget: Int? = null,
    val genres: List<MovieGenreDto>? = null,
    val homepage: String? = null,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    @SerializedName("tagline")
    val tagLine: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null
)