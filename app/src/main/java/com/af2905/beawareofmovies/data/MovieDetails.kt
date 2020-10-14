package com.af2905.beawareofmovies.data

import com.google.gson.annotations.SerializedName

class MovieDetails(
    @SerializedName("adult")
    val adult: Boolean? = null,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any? = null,

    @SerializedName("budget")
    val budget: Int? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("revenue")
    val revenue: Int? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("tagline")
    val tagLine: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null
) {
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0
        get() = field / 2

    @SerializedName("backdrop_path")
    val backdropPath: String? = null
        get() {
            return if (field != null) "https://image.tmdb.org/t/p/w500/$field" else null
        }
}