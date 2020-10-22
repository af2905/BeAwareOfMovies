package com.af2905.beawareofmovies.data.repository

import com.af2905.beawareofmovies.Constants.CATEGORY_NOW_PLAYING_MOVIES
import com.af2905.beawareofmovies.Constants.LANGUAGE_DEFAULT
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.mappers.to_entity.MovieVoToMovieEntity
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import io.reactivex.Single

class RemoteRepository(private val database: MovieDatabase) : MovieRepository<MovieVo> {
    override fun getMovies(): Single<List<MovieVo>> {
        return MovieApiClient.apiClient
            .getNowPlayingMovies(language = LANGUAGE_DEFAULT)
            .map { MovieMapper.toValueObject(it, CATEGORY_NOW_PLAYING_MOVIES) }
            .map {
                val movies = MovieVoToMovieEntity.toMovieEntity(it)
                database.movieDao().insertAll(movies)
                return@map it
            }
    }
}