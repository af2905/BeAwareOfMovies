package com.af2905.beawareofmovies

object VoteAverageConverter {
    fun convert(voteAverage: Float): Float {
        return voteAverage / 2
    }
}