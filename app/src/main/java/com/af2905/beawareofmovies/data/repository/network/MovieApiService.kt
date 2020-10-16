package com.af2905.beawareofmovies.data.repository.network

import com.af2905.beawareofmovies.BuildConfig
import com.af2905.beawareofmovies.data.repository.database.dto.ActorsResponseDto
import com.af2905.beawareofmovies.data.repository.database.dto.MovieDetails
import com.af2905.beawareofmovies.data.repository.database.dto.MoviesResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    //search/movie?api_key=<<api_key>>&language=ru-RU&include_adult=false
    @GET("search/movie")
    fun searchMoviesByQuery(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
        @Query(QUERY_PARAM_QUERY) query: String,
        @Query(QUERY_PARAM_INCLUDE_ADULT) adult: String = INCLUDE_ADULT_DEFAULT
    ): Single<MoviesResponseDto>

    //movie/popular?api_key=<<api_key>>&language=ru-RU
    @GET("movie/popular")
    fun getPopularMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponseDto>

    //movie/now_playing?api_key=<<api_key>>&language=ru-RU&page=1
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponseDto>

    //movie/upcoming?api_key=<<api_key>>&language=ru-RU
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponseDto>

    //movie/top_rated?api_key=<<api_key>>&language=ru-RU
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponseDto>

    //movie/{movie_id}?api_key=<<api_key>>&language=en-US
    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MovieDetails>

    //movie/479259/credits?api_key=<<api_key>>&language=ru-RU
    @GET("movie/{movie_id}/credits")
    fun getMovieActors(
        @Path("movie_id") movieId: String,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<ActorsResponseDto>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_QUERY = "query"
        private const val QUERY_PARAM_INCLUDE_ADULT = "include_adult"

        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        private const val INCLUDE_ADULT_DEFAULT = "false"
    }
}
