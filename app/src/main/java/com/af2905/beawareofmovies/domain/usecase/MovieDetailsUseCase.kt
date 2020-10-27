package com.af2905.beawareofmovies.domain.usecase

import com.af2905.beawareofmovies.data.repository.remote_repository.MovieActorsRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.MovieDetailsRemoteRepository
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MovieDetailsUseCase(private val movieId: Int, private val language: String) {
    fun getMovieDetailsPageData(): Observable<MovieDetailsPageData> {
        return Observable.zip(
            MovieDetailsRemoteRepository(movieId = movieId, language = language).getMovieDetails(),
            MovieActorsRemoteRepository(movieId = movieId, language = language).getActors(),
            BiFunction() { t1, t2 ->
                return@BiFunction MovieDetailsPageData(t1, t2)
            }
        )
            .applySchedulers()
    }
}


/* private fun downloadMovieDetails(movieId: Int) {
        var movieDetails: MovieDetailsVo? = null

        requestDisposable = Observable.zip(
            MovieDetailsRemoteRepository(movieId = movieId, language = language).getMovieDetails(),
            MovieActorsRemoteRepository(movieId = movieId, language = language).getActors(),
            BiFunction() { t1, t2 ->
                movieDetails = t1

                return@BiFunction listOf(
                    t2.map { actor -> ActorItem(actor) }
                        .toList().let { ActorCardContainer(R.string.actors, it) })
            }
        )
            .applySchedulers()
            .subscribe({
                setVisibilityForWidgets(View.VISIBLE)
                setVisibilityForProgressBar(View.GONE)

                movieDetails?.let {
                    movie_title_text_view.text = it.title
                    movie_rating.rating = it.voteAverage.toFloat()
                    overview_text_view.text = it.overview
                    movie_release_text_view.text = it.releaseDate?.getYearFromReleaseDate()
                    it.backdropPath?.let { url ->
                        PicassoClient.downloadImage(url, backdrop_image_view)
                    }
                }
                if (movieDetails?.productionCompanies.isNullOrEmpty()) {
                    setVisibilityForProductionCompanies(View.GONE)
                } else {
                    setVisibilityForProductionCompanies(View.VISIBLE)
                    val companies = movieDetails?.productionCompanies?.map { it.name }
                    production_company_text_view.text =
                        companies?.joinToString(separator = COMMA_SEPARATOR)
                }
                val genres = movieDetails?.genres?.map { it.name }
                movie_genre_text_view.text = genres?.joinToString(separator = COMMA_SEPARATOR)

                actors_recycler_view.adapter = adapter.apply { addAll(it) }
            }, {

            })
    }*/