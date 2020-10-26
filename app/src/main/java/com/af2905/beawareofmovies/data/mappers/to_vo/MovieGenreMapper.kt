package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.MovieGenreDto
import com.af2905.beawareofmovies.data.vo.MovieGenreVo

object MovieGenreMapper {
    fun toValueObject(dto: MovieGenreDto): MovieGenreVo {
        return MovieGenreVo(id = dto.id, name = dto.name)
    }
}