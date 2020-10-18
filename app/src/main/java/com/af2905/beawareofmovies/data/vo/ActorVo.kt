package com.af2905.beawareofmovies.data.vo

data class ActorVo(
    val id: Int? = null,
    val name: String? = null,
) {
    var profilePath: String? = null
        get() {
            return if (field != null) "https://image.tmdb.org/t/p/w500/$field" else null
        }
}