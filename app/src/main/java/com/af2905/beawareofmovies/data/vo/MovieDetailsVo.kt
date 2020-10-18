package com.af2905.beawareofmovies.data.vo

data class MovieDetailsVo(
    val id: Int? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
) {
    var voteAverage: Double = 0.0
        get() = field / 2

    var backdropPath: String? = null
        get() {
            return if (field != null) "https://image.tmdb.org/t/p/w500/$field" else null
        }
}