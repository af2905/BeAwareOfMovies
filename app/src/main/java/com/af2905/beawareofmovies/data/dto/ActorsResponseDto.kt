package com.af2905.beawareofmovies.data.dto

import com.google.gson.annotations.SerializedName


data class ActorsResponseDto(
    val id: Int,
    @SerializedName("cast")
    val actors: List<MovieActorDto>? = null
)