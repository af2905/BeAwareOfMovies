package com.af2905.beawareofmovies.data

import com.google.gson.annotations.SerializedName


class ActorsResponse {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("cast")
    val actors: List<Actor>? = null
}