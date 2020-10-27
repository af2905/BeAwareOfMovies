package com.af2905.beawareofmovies.presentation.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.af2905.beawareofmovies.Constants
import com.af2905.beawareofmovies.Constants.SEARCH_QUERY
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.repository.remote_repository.PopularRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.SearchMoviesRemoteRepository
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feed_header.*
import kotlinx.android.synthetic.main.fragment_search.*
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
                    0 -> downloadPopularMoviesByDefault()
                    else -> downloadRequestedMovies(it)
                }
            }, {
            })
        )
    }

    private fun downloadRequestedMovies(searchTerm: String) {
        compositeDisposable.add(
            SearchMoviesRemoteRepository(searchTerm = searchTerm, language = language).getMovies()
                .map { movies -> movies.filter { it.posterPath != null } }
                .map { movies -> movies.sortedByDescending { it.releaseDate } }
                .applySchedulers()
                .subscribe({ movies ->
                    adapter.clear()
                    movies.map { movie ->
                        val list = listOf(SearchItem(movie) { openMovieDetails(movie) })
                        movies_recycler_view.adapter = adapter.apply { addAll(list) }
                    }
                }, {
                })
        )
    }

    private fun downloadPopularMoviesByDefault() {
        compositeDisposable.add(PopularRemoteRepository(language = language).getMovies()
            .map { movies -> movies.filter { it.posterPath != null } }
            .applySchedulers()
            .subscribe({ movies ->
                adapter.clear()
                movies.map { movie ->
                    val list = listOf(SearchItem(movie) { openMovieDetails(movie) })
                    movies_recycler_view.adapter = adapter.apply { addAll(list) }
                }
            }, {
            })
        )
    }

    private fun openMovieDetails(movie: MovieVo) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val bundle = Bundle()
        bundle.putInt(Constants.MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        compositeDisposable.clear()
        adapter.clear()
    }
}