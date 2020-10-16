package com.af2905.beawareofmovies.data.repository.database.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("page")
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("results")
    val results: List<Movie>
)