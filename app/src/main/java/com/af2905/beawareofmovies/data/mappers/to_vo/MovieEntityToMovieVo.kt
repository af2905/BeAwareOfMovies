package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.database.entity.MovieEntity
import com.af2905.beawareofmovies.data.vo.MovieVo

object MovieEntityToMovieVo {
    fun toMovieVo(entities: List<MovieEntity>): List<MovieVo> {
        return entities.map {
            val movie = MovieVo(
                id = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                category = it.category
            )
            movie.voteAverage = it.voteAverage
            movie.posterPath = it.posterPath
            return@map movie
        }
    }
}