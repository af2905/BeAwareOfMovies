package com.af2905.beawareofmovies.domain.repository

import io.reactivex.Single

interface MovieDbRepository <T> {
    fun getMoviesFromDb(): Single<List<T>>
}