package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.ActorDto
import com.af2905.beawareofmovies.data.dto.ActorsResponseDto
import com.af2905.beawareofmovies.data.vo.ActorVo

object ActorMapper {
    fun toValueObject(dto: ActorsResponseDto): List<ActorVo>? {
        return dto.actors?.map { toValueObject(it) }
    }

    private fun toValueObject(dto: ActorDto): ActorVo {
        with(dto) {
            val actor = ActorVo(id = id, name = name)
            actor.profilePath = profilePath
            return actor
        }
    }
}