package com.af2905.beawareofmovies.domain.repository

import io.reactivex.Single

interface MoviesRemoteRepository<T> {
    fun getMoviesFromNet(): Single<List<T>>
}