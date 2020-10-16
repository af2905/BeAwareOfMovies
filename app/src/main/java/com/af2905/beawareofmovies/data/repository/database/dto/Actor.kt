package com.af2905.beawareofmovies.data.repository.database.dto

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("cast_id")
    val castId: Int? = null,

    @SerializedName("character")
    val character: String? = null,

    @SerializedName("credit_id")
    val creditId: String? = null,

    @SerializedName("gender")
    val gender: Int? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("order")
    val order: Int? = null
) {
    @SerializedName("profile_path")
    val profilePath: String? = null
        get() {
            return if (field != null) "https://image.tmdb.org/t/p/w500/$field" else null
        }
}