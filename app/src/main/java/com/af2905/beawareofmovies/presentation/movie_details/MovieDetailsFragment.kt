package com.af2905.beawareofmovies.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.af2905.beawareofmovies.Constants.COMMA_SEPARATOR
import com.af2905.beawareofmovies.Constants.MOVIE_ID
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.network.PicassoClient
import com.af2905.beawareofmovies.data.repository.remote_repository.MovieActorsRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.MovieDetailsRemoteRepository
import com.af2905.beawareofmovies.data.vo.MovieDetailsVo
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import com.af2905.beawareofmovies.util.extensions.getYearFromReleaseDate
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.movie_details_header.*

class MovieDetailsFragment : Fragment() {
    private lateinit var language: String
    private var requestDisposable = Disposables.empty()
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibilityForWidgets(View.GONE)
        setVisibilityForProductionCompanies(View.GONE)
        setVisibilityForProgressBar(View.VISIBLE)
        language = resources.getString(R.string.language)
        val movieId = requireArguments().getInt(MOVIE_ID)
        downloadMovieDetails(movieId)
    }

    private fun downloadMovieDetails(movieId: Int) {
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
    }

    private fun setVisibilityForWidgets(visibility: Int) {
        movie_title_text_view.visibility = visibility
        movie_rating.visibility = visibility
        full_hd_image_view.visibility = visibility
        watch_button.visibility = visibility
        heart_checkbox.visibility = visibility
        backdrop_image_view.visibility = visibility
        overview_text_view.visibility = visibility
        title_movie_release_text_view.visibility = visibility
        movie_release_text_view.visibility = visibility
        title_movie_genre_text_view.visibility = visibility
        movie_genre_text_view.visibility = visibility
    }

    private fun setVisibilityForProductionCompanies(visibility: Int) {
        title_production_company_text_view.visibility = visibility
        production_company_text_view.visibility = visibility
    }

    private fun setVisibilityForProgressBar(visibility: Int) {
        progress_bar.visibility = visibility
    }

    override fun onStop() {
        super.onStop()
        requestDisposable.dispose()
    }
}