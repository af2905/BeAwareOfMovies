package com.af2905.beawareofmovies.data

object MockRepository {

    fun getMovies(): List<Movie> {
        val moviesList = mutableListOf<Movie>()
        for (x in 0..10) {
            val movie = Movie(title = "Spider-Man $x", id = x + 1)
            moviesList.add(movie)
        }
        return moviesList
    }
}