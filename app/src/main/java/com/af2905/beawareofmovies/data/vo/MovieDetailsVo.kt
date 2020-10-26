package com.af2905.beawareofmovies.data.vo

import com.af2905.beawareofmovies.util.extensions.fiveStarRating
import com.af2905.beawareofmovies.util.extensions.getFullPathToImage

data class MovieDetailsVo(
    val id: Int? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
) {
    var voteAverage: Double = 0.0
        get() = field.fiveStarRating()

    var backdropPath: String? = null
        get() = field.getFullPathToImage()
}