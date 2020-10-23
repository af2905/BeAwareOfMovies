package com.af2905.beawareofmovies.data.repository.local_repository

import com.af2905.beawareofmovies.Constants.CATEGORY_NOW_PLAYING_MOVIES
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.database.entity.MovieEntity
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import io.reactivex.Single

class NowPlayingLocalRepository(private val database: MovieDatabase) : MovieRepository<MovieEntity> {
    override fun getMovies(): Single<List<MovieEntity>> {
        return Single.just(database.movieDao().getAll(CATEGORY_NOW_PLAYING_MOVIES))
    }
}