package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.MovieDetailsDto
import com.af2905.beawareofmovies.data.vo.MovieDetailsVo

object MovieDetailsMapper {
    fun toValueObject(dto: MovieDetailsDto): MovieDetailsVo {
        with(dto) {
            val movieDetail = MovieDetailsVo(
                id = id, overview = overview, releaseDate = releaseDate, title = title
            )
            movieDetail.voteAverage = voteAverage
            movieDetail.backdropPath = backdropPath
            return movieDetail
        }
    }
}