package com.af2905.beawareofmovies.data.mappers.to_vo

import com.af2905.beawareofmovies.data.dto.ProductionCompanyDto
import com.af2905.beawareofmovies.data.vo.ProductionCompanyVo

object ProductionCompanyMapper {
    fun toValueObject(dto: ProductionCompanyDto): ProductionCompanyVo {
        return ProductionCompanyVo(id = dto.id, name = dto.name)
    }
}