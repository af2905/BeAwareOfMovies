package com.af2905.beawareofmovies.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.af2905.beawareofmovies.Constants.COMMA_SEPARATOR
import com.af2905.beawareofmovies.Constants.MOVIE_ID
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.MovieDetails
import com.af2905.beawareofmovies.network.MovieApiClient
import com.af2905.beawareofmovies.network.PicassoClient
import com.af2905.beawareofmovies.ui.extensions.applySchedulers
import com.af2905.beawareofmovies.ui.extensions.getYearFromReleaseDate
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Single
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
        language = resources.getString(R.string.language)
        val movieId = requireArguments().getInt(MOVIE_ID)
        downloadMovieDetails(movieId)
    }

    private fun downloadMovieDetails(movieId: Int) {
        val apiClient = MovieApiClient.apiClient
        var movieDetails: MovieDetails? = null
        val companies: MutableList<String> = mutableListOf()
        val genres: MutableList<String> = mutableListOf()
        requestDisposable = Single.zip(
            apiClient.getMovieDetails(movieId = movieId.toString(), language = language),
            apiClient.getMovieActors(movieId = movieId.toString(), language = language),
            BiFunction { t1, t2 ->
                movieDetails = t1
                t1.productionCompanies?.map { it.name?.let { name -> companies.add(name) } }
                t1.genres?.map { it.name?.let { name -> genres.add(name) } }
                return@BiFunction listOf(t2.actors
                    ?.filter { actor -> actor.profilePath != null }
                    ?.map { actor -> ActorItem(actor) }
                    ?.toList()?.let { ActorCardContainer(R.string.actors, it) })
            }
        )
            .applySchedulers()
            .subscribe({
                movieDetails?.let {
                    movie_title_text_view.text = it.title
                    movie_rating.rating = it.voteAverage.toFloat()
                    overview_text_view.text = it.overview
                    movie_release_text_view.text = it.releaseDate?.getYearFromReleaseDate()
                    it.backdropPath?.let { url ->
                        PicassoClient.downloadImage(url, backdrop_image_view)
                    }
                }
                if (companies.isNotEmpty()) {
                    production_company_text_view.text =
                        companies.joinToString(separator = COMMA_SEPARATOR)
                } else {
                    production_company_title.visibility = View.GONE
                    production_company_text_view.visibility = View.GONE
                }
                movie_genre_text_view.text = genres.joinToString(separator = COMMA_SEPARATOR)
                actors_recycler_view.adapter = adapter.apply { addAll(it) }
            }, {

            })
    }

    override fun onStop() {
        super.onStop()
        requestDisposable.dispose()
    }
}