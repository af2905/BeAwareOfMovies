package com.af2905.beawareofmovies.data.repository

import com.af2905.beawareofmovies.data.dto.MovieDto

object MockRepository {

    fun getMovies(): List<MovieDto> {
        val moviesList = mutableListOf<MovieDto>()
        for (x in 0..10) {
            val movie = MovieDto(title = "Spider-Man $x", id = x + 1)
            moviesList.add(movie)
        }
        return moviesList
    }
}