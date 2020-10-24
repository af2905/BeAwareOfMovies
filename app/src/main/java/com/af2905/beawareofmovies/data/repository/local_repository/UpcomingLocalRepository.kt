package com.af2905.beawareofmovies.data.repository.local_repository

import com.af2905.beawareofmovies.Constants.CATEGORY_UPCOMING_MOVIES
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieEntityToMovieVo
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import io.reactivex.Observable

class UpcomingLocalRepository(private val database: MovieDatabase) : MovieRepository<MovieVo> {

    override fun getMovies(): Observable<List<MovieVo>> {
        return database.movieDao().getAll(CATEGORY_UPCOMING_MOVIES)
            .map { MovieEntityToMovieVo.toValueObject(it) }
    }
}