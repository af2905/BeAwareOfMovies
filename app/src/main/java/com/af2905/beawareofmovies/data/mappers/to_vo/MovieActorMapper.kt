package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.ActorsResponseDto
import com.af2905.beawareofmovies.data.dto.MovieActorDto
import com.af2905.beawareofmovies.data.vo.MovieActorVo

object MovieActorMapper {
    fun toValueObject(dto: ActorsResponseDto): List<MovieActorVo>? {
        return if (dto.actors != null) dto.actors
            .filterNot { it.profilePath.isNullOrEmpty() }
            .map { toValueObject(it) }
        else emptyList()
    }

    fun toValueObject(dto: MovieActorDto): MovieActorVo {
        with(dto) {
            val actor = MovieActorVo(id = id, name = name)
            actor.profilePath = profilePath
            return actor
        }
    }
}