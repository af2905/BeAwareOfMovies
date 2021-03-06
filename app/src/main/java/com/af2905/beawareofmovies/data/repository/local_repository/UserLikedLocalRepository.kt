package com.af2905.beawareofmovies.data.repository.local_repository

import com.af2905.beawareofmovies.Constants.CATEGORY_USER_LIKED_MOVIES
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.mappers.to_vo.MovieEntityToMovieVo
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.repository.MovieRepository
import io.reactivex.Observable

class UserLikedLocalRepository(private val database: MovieDatabase) : MovieRepository<MovieVo> {

    override fun getMovies(): Observable<List<MovieVo>> {
        return database.movieDao().getAll(CATEGORY_USER_LIKED_MOVIES)
            .map { MovieEntityToMovieVo.toValueObject(it) }
    }
}