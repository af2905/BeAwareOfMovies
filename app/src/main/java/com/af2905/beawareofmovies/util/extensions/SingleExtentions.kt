package com.af2905.beawareofmovies.util.extensions

import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.mappers.to_entity.MovieVoToMovieEntity
import com.af2905.beawareofmovies.data.vo.MovieVo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Single<List<MovieVo>>.addMoviesInDatabaseAndReturn(database: MovieDatabase): Single<List<MovieVo>> {
    return this.map {
        val movies = MovieVoToMovieEntity.toMovieEntity(it)
        database.movieDao().insertAll(movies)
        return@map it
    }
}