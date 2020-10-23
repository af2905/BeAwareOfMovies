package com.af2905.beawareofmovies.data.repository.remote_repository

import com.af2905.beawareofmovies.Constants.CATEGORY_TOP_RATED_MOVIES
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieMapper
import com.af2905.beawareofmovies.data.network.MovieApiClient
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import com.af2905.beawareofmovies.util.extensions.addMoviesInDatabaseAndReturn
import io.reactivex.Single

class TopRatedRemoteRepository(private val database: MovieDatabase) : MovieRepository<MovieVo> {
    override fun getMovies(): Single<List<MovieVo>> {
        return MovieApiClient.apiClient
            .getTopRatedMovies()
            .map { MovieMapper.toValueObject(it, CATEGORY_TOP_RATED_MOVIES) }
            .addMoviesInDatabaseAndReturn(database)
    }
}