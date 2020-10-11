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
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.movie_details_header.*

class MovieDetailsFragment : Fragment() {
    private lateinit var language: String
    private var requestDisposable = Disposables.empty()

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
        requestDisposable = MovieApiClient.apiClient.getMovieDetails(
            movieId = movieId.toString(),
            language = language
        )
            .applySchedulers()
            .subscribe({
                it.backdropPath?.let { url ->
                    PicassoClient.downloadImage(
                        url,
                        backdrop_image_view
                    )
                }
                movie_title_text_view.text = it.title
                movie_rating.rating = it.voteAverage.toFloat()
                overview_text_view.text = it.overview
                movie_release_text_view.text = it.releaseDate?.getYearFromReleaseDate()

            }, {
            })
    }

    override fun onStop() {
        super.onStop()
        requestDisposable.dispose()
    }
}