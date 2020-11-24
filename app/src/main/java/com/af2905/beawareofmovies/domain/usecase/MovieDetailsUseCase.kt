package com.af2905.beawareofmovies.domain.usecase

import com.af2905.beawareofmovies.data.repository.remote_repository.MovieActorsRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.MovieDetailsRemoteRepository
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MovieDetailsUseCase(private val movieId: Int, private val language: String) {
    fun getMovieDetailsPageData(): Observable<MovieDetailsPageData> {
        return Observable.zip(
            MovieDetailsRemoteRepository(movieId = movieId, language = language).getMovieDetails(),
            MovieActorsRemoteRepository(movieId = movieId, language = language).getActors(),
            BiFunction() { movieDetails, movieActors ->
                return@BiFunction MovieDetailsPageData(movieDetails, movieActors)
            }
        ).applySchedulers()
    }
}