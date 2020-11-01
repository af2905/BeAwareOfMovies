package com.af2905.beawareofmovies.domain.usecase


import com.af2905.beawareofmovies.data.repository.remote_repository.NowPlayingRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.PopularRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.TopRatedRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.UpcomingRemoteRepository
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import io.reactivex.Observable
import io.reactivex.functions.Function4

class FeedUseCase {
    fun getZippedMovies(language: String): Observable<HashMap<MovieCategories, List<MovieVo>>> {
        return Observable.zip(
            PopularRemoteRepository(language = language).getMovies(),
            TopRatedRemoteRepository(language = language).getMovies(),
            NowPlayingRemoteRepository(language = language).getMovies(),
            UpcomingRemoteRepository(language = language).getMovies(),
            Function4 { popular, topRated, nowPlaying, upcoming ->
                return@Function4 hashMapOf(
                    MovieCategories.POPULAR to popular, MovieCategories.TOP_RATED to topRated,
                    MovieCategories.NOW_PLAYING to nowPlaying, MovieCategories.UPCOMING to upcoming
                )
            }
        ).applySchedulers()
    }

    enum class MovieCategories {
        NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
    }
}