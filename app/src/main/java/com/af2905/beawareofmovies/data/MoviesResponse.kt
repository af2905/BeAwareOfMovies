package com.af2905.beawareofmovies.data

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    private val page: Int,

    @SerializedName("total_results")
    private val totalResults: Int,

    @SerializedName("total_pages")
    private val totalPages: Int,

    @SerializedName("results")
    private val results: List<Movie>
)