package com.af2905.beawareofmovies.data.dto

import com.google.gson.annotations.SerializedName

data class MovieActorDto(
    @SerializedName("cast_id")
    val castId: Int? = null,
    val character: String? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    val gender: Int? = null,
    val id: Int,
    val name: String? = null,
    val order: Int? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)