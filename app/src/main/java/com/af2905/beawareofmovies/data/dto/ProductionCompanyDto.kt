package com.af2905.beawareofmovies.data.dto

import com.google.gson.annotations.SerializedName

data class ProductionCompanyDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: Any? = null,
    val name: String? = null,
    @SerializedName("origin_country")
    val originCountry: String? = null
)