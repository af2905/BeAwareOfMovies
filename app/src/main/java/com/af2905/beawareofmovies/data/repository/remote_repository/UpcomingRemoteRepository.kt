package com.af2905.beawareofmovies.data.repository.remote_repository

import com.af2905.beawareofmovies.Constants.CATEGORY_UPCOMING_MOVIES
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import io.reactivex.Observable

class UpcomingRemoteRepository(private val language: String) : MovieRepository<MovieVo> {
    override fun getMovies(): Observable<List<MovieVo>> {
        return MovieApiClient.apiClient
            .getUpcomingMovies(language = language)
            .map { MovieMapper.toValueObject(it, CATEGORY_UPCOMING_MOVIES) }
            .toObservable()
            .map { return@map it }
    }
}