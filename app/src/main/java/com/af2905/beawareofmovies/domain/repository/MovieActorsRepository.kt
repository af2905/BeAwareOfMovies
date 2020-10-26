package com.af2905.beawareofmovies.domain.repository

import io.reactivex.Observable

interface MovieActorsRepository<T> {
    fun getActors(): Observable<List<T>>
}