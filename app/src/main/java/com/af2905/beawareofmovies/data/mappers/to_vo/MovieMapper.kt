package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.MovieDto
import com.af2905.beawareofmovies.data.dto.MoviesResponseDto
import com.af2905.beawareofmovies.data.vo.MovieVo

object MovieMapper {
    fun toValueObject(dto: MoviesResponseDto): List<MovieVo> {
        return if (dto.results != null) dto.results.map { toValueObject(it) } else emptyList()
    }

    private fun toValueObject(dto: MovieDto): MovieVo {
        with(dto) {
            val movie = MovieVo(
                id = id, title = title, overview = overview, releaseDate = releaseDate
            )
            movie.voteAverage = voteAverage
            movie.posterPath = posterPath
            return movie
        }
    }
}