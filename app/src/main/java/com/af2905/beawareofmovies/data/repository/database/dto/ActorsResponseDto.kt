package com.af2905.beawareofmovies.data.repository.database.dto

import com.google.gson.annotations.SerializedName


class ActorsResponseDto {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("cast")
    val actors: List<Actor>? = null
}