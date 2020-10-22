package com.af2905.beawareofmovies.data.mappers.to_entity

import com.af2905.beawareofmovies.data.database.entity.MovieEntity
import com.af2905.beawareofmovies.data.vo.MovieVo

object MovieVoToMovieEntity {
    fun toMovieEntity(valueObjects: List<MovieVo>): List<MovieEntity> {
        return valueObjects.map { movie ->
            MovieEntity(
                id = movie.id ?: -1,
                title = movie.title,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                posterPath = movie.posterPath,
                category = movie.category
            )
        }
    }
}

