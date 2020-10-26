package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.GenreDto
import com.af2905.beawareofmovies.data.vo.GenreVo

object GenreMapper {
    fun toValueObject(dto: GenreDto): GenreVo {
        return GenreVo(id = dto.id, name = dto.name)
    }
}