package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.MovieDto
import com.af2905.beawareofmovies.data.dto.MoviesResponseDto
import com.af2905.beawareofmovies.data.vo.MovieVo

object MovieMapper {
    fun toValueObject(dto: MoviesResponseDto, category: String): List<MovieVo> {
        return if (dto.results != null) dto.results
            .filterNot { it.title.isNullOrEmpty() }
            .filterNot { it.overview.isNullOrEmpty() }
            .filterNot { it.posterPath.isNullOrEmpty() }
            .map {
                toValueObject(it, category)
            } else emptyList()
    }

    private fun toValueObject(dto: MovieDto, category: String): MovieVo {
        with(dto) {
            val movie = MovieVo(
                id = id,
                title = title,
                overview = overview,
                releaseDate = releaseDate,
                category = category
            )
            movie.voteAverage = voteAverage
            movie.posterPath = posterPath
            return movie
        }
    }
}