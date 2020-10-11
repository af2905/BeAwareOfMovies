package com.af2905.beawareofmovies.ui.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.af2905.beawareofmovies.Constants.SEARCH_QUERY
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.Movie
import com.af2905.beawareofmovies.network.MovieApiClient
import com.af2905.beawareofmovies.ui.extensions.applySchedulers
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {
    private var compositeDisposable = CompositeDisposable()
    private lateinit var language: String
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchTerm = requireArguments().getString(SEARCH_QUERY) ?: ""
        search_toolbar.setText(searchTerm)
        language = resources.getString(R.string.language)
        showRequestedMoviesWhenViewCreated(searchTerm)
        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        getSearchQuery()
    }

    private fun showRequestedMoviesWhenViewCreated(searchTerm: String) {
        downloadRequestedMovies(searchTerm)
    }

    private fun getSearchQuery() {
        compositeDisposable.add(search_toolbar.onTextChangedObservable
            .map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .applySchedulers()
            .subscribe({
                when (it.length) {
                    0 -> {
                        backToFeedFragmentWhenSearchIsEmpty()
                    }
                    else -> downloadRequestedMovies(it)
                }
            }, {

            })
        )
    }

    private fun downloadRequestedMovies(searchTerm: String) {
        compositeDisposable.add(
            MovieApiClient.apiClient.searchMoviesByQuery(query = searchTerm, language = language)
                .map { moviesResponse -> moviesResponse.results }
                .map { movies -> movies.filter { it.posterPath != null } }
                .map { movies -> movies.sortedByDescending { it.releaseDate } }
                .applySchedulers()
                .subscribe({ movies ->
                    Timber.tag("TEST_OF_LOADING_DATA").d(movies.toString())
                    adapter.clear()
                    movies.map { movie ->
                        val list = listOf(SearchItem(movie) { openMovieDetails(movie) })
                        movies_recycler_view.adapter = adapter.apply { addAll(list) }
                    }
                }, {
                    Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
                })
        )
    }

    private fun backToFeedFragmentWhenSearchIsEmpty() {
        findNavController().navigate(R.id.search_dest)
    }

    private fun openMovieDetails(movie: Movie) {
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        compositeDisposable.clear()
        adapter.clear()
    }
}