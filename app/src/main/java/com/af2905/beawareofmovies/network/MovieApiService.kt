package com.af2905.beawareofmovies.network

import com.af2905.beawareofmovies.BuildConfig
import com.af2905.beawareofmovies.data.ActorsResponse
import com.af2905.beawareofmovies.data.MovieDetails
import com.af2905.beawareofmovies.data.MoviesResponse
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
    ): Single<MoviesResponse>

    //movie/popular?api_key=<<api_key>>&language=ru-RU
    @GET("movie/popular")
    fun getPopularMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponse>

    //movie/now_playing?api_key=<<api_key>>&language=ru-RU&page=1
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponse>

    //movie/upcoming?api_key=<<api_key>>&language=ru-RU
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponse>

    //movie/top_rated?api_key=<<api_key>>&language=ru-RU
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String,
    ): Single<MoviesResponse>

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
    ): Single<ActorsResponse>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_QUERY = "query"
        private const val QUERY_PARAM_INCLUDE_ADULT = "include_adult"

        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        private const val INCLUDE_ADULT_DEFAULT = "false"
    }
}
