package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.MovieDetailsDto
import com.af2905.beawareofmovies.data.vo.MovieDetailsVo

object MovieDetailsMapper {
    fun toValueObject(dto: MovieDetailsDto): MovieDetailsVo {
        with(dto) {
            val productionCompanies = productionCompanies?.let {
                (it.map { company -> ProductionCompanyMapper.toValueObject(company) })
            }
            val genres = genres?.let { it.map { genre -> MovieGenreMapper.toValueObject(genre) } }

            val movieDetail = MovieDetailsVo(
                id = id, overview = overview, releaseDate = releaseDate, title = title,
                productionCompanies = productionCompanies, genres = genres
            )
            movieDetail.voteAverage = voteAverage
            movieDetail.backdropPath = backdropPath
            return movieDetail
        }
    }
}