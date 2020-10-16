package com.af2905.beawareofmovies.data.repository.database.dto

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)