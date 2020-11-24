package com.af2905.beawareofmovies.domain.usecase

import com.af2905.beawareofmovies.data.vo.MovieActorVo
import com.af2905.beawareofmovies.data.vo.MovieDetailsVo

data class MovieDetailsPageData(val details: MovieDetailsVo, val actors: List<MovieActorVo>)