package com.af2905.beawareofmovies.data.repository

import com.af2905.beawareofmovies.Constants.LANGUAGE_DEFAULT
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.database.entity.NowPlayingMovieEntity
import com.af2905.beawareofmovies.data.mappers.to_entity.MovieVoToNowPlayingMovieMapper
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieDbRepository
import com.af2905.beawareofmovies.domain.repository.MoviesRemoteRepository
import io.reactivex.Single

class NowPlayingMoviesRepository(private val database: MovieDatabase) :
    MoviesRemoteRepository<MovieVo>,
    MovieDbRepository<NowPlayingMovieEntity> {
    override fun getMoviesFromNet(): Single<List<MovieVo>> {
        return MovieApiClient.apiClient
            .getNowPlayingMovies(language = LANGUAGE_DEFAULT)
            .map {
                MovieMapper.toValueObject(it)
            }
            .map {
                val movies = MovieVoToNowPlayingMovieMapper.toNowPlayingMovieEntity(it)
                database.nowPlayingMovieDao().insertAll(movies)
                return@map it
            }
    }

    override fun getMoviesFromDb(): Single<List<NowPlayingMovieEntity>> {
        return Single.just(database.nowPlayingMovieDao().getAll())
    }
}