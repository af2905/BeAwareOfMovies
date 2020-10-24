package com.af2905.beawareofmovies.data.repository.remote_repository

import com.af2905.beawareofmovies.data.mappers.to_vo.MovieDetailsMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieDetailsVo
import com.af2905.beawareofmovies.domain.repository.MovieDetailsRepository
import io.reactivex.Observable

class MovieDetailsRemoteRepository(private val movieId: Int, private val language: String) :
    MovieDetailsRepository<MovieDetailsVo> {
    override fun getMovieDetails(): Observable<MovieDetailsVo> {
        return MovieApiClient.apiClient
            .getMovieDetails(movieId = movieId.toString(), language = language)
            .map { MovieDetailsMapper.toValueObject(it) }
            .toObservable()
    }
}