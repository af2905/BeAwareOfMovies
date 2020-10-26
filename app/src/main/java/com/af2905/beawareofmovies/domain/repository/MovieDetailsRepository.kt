package com.af2905.beawareofmovies.domain.repository

import io.reactivex.Observable

interface MovieDetailsRepository<T> {
    fun getMovieDetails(): Observable<T>
}