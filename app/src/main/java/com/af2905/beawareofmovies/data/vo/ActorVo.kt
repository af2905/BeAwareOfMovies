package com.af2905.beawareofmovies.data.vo

import com.af2905.beawareofmovies.util.extensions.getFullPathToImage

data class ActorVo(
    val id: Int? = null,
    val name: String? = null,
) {
    var profilePath: String? = null
        get() = field.getFullPathToImage()
}