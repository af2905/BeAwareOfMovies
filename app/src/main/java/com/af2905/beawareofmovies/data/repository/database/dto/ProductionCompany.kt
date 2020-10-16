package com.af2905.beawareofmovies.data.repository.database.dto

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("logo_path")
    val logoPath: Any? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("origin_country")
    val originCountry: String? = null
)