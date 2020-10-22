package com.af2905.beawareofmovies.domain.repository

import io.reactivex.Single

interface MovieRepository<T> {
    fun getMovies(): Single<List<T>>
}