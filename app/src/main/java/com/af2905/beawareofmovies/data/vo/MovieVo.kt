package com.af2905.beawareofmovies.data.vo

import com.af2905.beawareofmovies.util.extensions.fiveStarRating
import com.af2905.beawareofmovies.util.extensions.getFullPathToImage

data class MovieVo(
    val id: Int,
    val title: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    var category: String
) {
    var voteAverage: Double = 0.0
        get() = field.fiveStarRating()

    var posterPath: String? = null
        get() = field.getFullPathToImage()
}