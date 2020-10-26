package com.af2905.beawareofmovies.domain.repository

import io.reactivex.Observable

interface MovieRepository<T> {
    fun getMovies(): Observable<List<T>>
}