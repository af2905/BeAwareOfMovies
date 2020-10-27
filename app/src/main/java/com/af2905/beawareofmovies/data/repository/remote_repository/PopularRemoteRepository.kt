package com.af2905.beawareofmovies.data.repository.remote_repository

import com.af2905.beawareofmovies.Constants.CATEGORY_POPULAR_MOVIES
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import io.reactivex.Observable

class PopularRemoteRepository(private val language: String) : MovieRepository<MovieVo> {
    override fun getMovies(): Observable<List<MovieVo>> {
        return MovieApiClient.apiClient
            .getPopularMovies(language = language)
            .map { MovieMapper.toValueObject(it, CATEGORY_POPULAR_MOVIES) }
            .map { movies -> movies.filter { it.posterPath != null } }
            .toObservable()
    }
}