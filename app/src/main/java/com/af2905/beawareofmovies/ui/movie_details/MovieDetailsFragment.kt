package com.af2905.beawareofmovies.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.af2905.beawareofmovies.Constants.MOVIE_ID
import com.af2905.beawareofmovies.R
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
        var movieTitle = ""
        var movieRating = 0.0
        var movieOverview = ""
        var movieRelease = ""
        var movieUrl = ""
        requestDisposable = Single.zip(
            apiClient.getMovieDetails(movieId = movieId.toString(), language = language),
            apiClient.getMovieActors(movieId = movieId.toString(), language = language),
            BiFunction { t1, t2 ->
                movieTitle = t1.title.toString()
                movieRating = t1.voteAverage
                movieOverview = t1.overview.toString()
                movieRelease = t1.releaseDate?.getYearFromReleaseDate().toString()
                movieUrl = t1.backdropPath.toString()
                return@BiFunction listOf(t2.actors
                    ?.filter { actor -> actor.profilePath != null }
                    ?.map { actor -> ActorItem(actor) }
                    ?.toList()?.let { ActorCardContainer(R.string.actors, it) })
            }
        )
            .applySchedulers()
            .subscribe({
                movie_title_text_view.text = movieTitle
                movie_rating.rating = movieRating.toFloat()
                overview_text_view.text = movieOverview
                movie_release_text_view.text = movieRelease
                PicassoClient.downloadImage(movieUrl, backdrop_image_view)
                actors_recycler_view.adapter = adapter.apply { addAll(it) }
            },
                {})
    }

    override fun onStop() {
        super.onStop()
        requestDisposable.dispose()
    }
}