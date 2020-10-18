package com.af2905.beawareofmovies.data.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    val page: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    val results: List<MovieDto>? = null
)