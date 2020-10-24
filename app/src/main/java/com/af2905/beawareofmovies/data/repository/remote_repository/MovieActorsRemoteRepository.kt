package com.af2905.beawareofmovies.data.repository.remote_repository

import com.af2905.beawareofmovies.data.mappers.to_vo.MovieActorMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieActorVo
import com.af2905.beawareofmovies.domain.repository.MovieActorsRepository
import io.reactivex.Observable

class MovieActorsRemoteRepository(private val movieId: Int, private val language: String) :
    MovieActorsRepository<MovieActorVo> {
    override fun getActors(): Observable<List<MovieActorVo>> {
        return MovieApiClient.apiClient
            .getMovieActors(movieId = movieId.toString(), language = language)
            .map {
                val actors = it.actors ?: emptyList()
                actors.map { actor -> MovieActorMapper.toValueObject(actor) }
            }
            .toObservable()
    }
}