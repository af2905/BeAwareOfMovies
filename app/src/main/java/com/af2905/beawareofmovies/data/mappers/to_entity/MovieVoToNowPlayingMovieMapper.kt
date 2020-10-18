package com.af2905.beawareofmovies.data.mappers.to_entity

import com.af2905.beawareofmovies.data.database.entity.NowPlayingMovieEntity
import com.af2905.beawareofmovies.data.vo.MovieVo

object MovieVoToNowPlayingMovieMapper {
    fun toNowPlayingMovieEntity(valueObjects: List<MovieVo>): List<NowPlayingMovieEntity> {
        return valueObjects.map { movie ->
            NowPlayingMovieEntity(
                id = movie.id ?: -1,
                title = movie.title,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                voteAverage = movie.voteAverage,
                posterPath = movie.posterPath
            )
        }
    }
}

