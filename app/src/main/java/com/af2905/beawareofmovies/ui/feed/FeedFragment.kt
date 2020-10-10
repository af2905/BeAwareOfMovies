package com.af2905.beawareofmovies.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.Movie
import com.af2905.beawareofmovies.network.MovieApiClient
import com.af2905.beawareofmovies.ui.observeOnMainThread
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FeedFragment : Fragment() {
    private var compositeDisposable = CompositeDisposable()
    private lateinit var language: String
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        language = resources.getString(R.string.language)
        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        getSearchQuery()
        getPopularMovies()
        getNowPlayingMovies()
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getSearchQuery() {
        compositeDisposable.add(search_toolbar.onTextChangedObservable
            .map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .filter { it.length > 2 }
            .observeOnMainThread()
            .subscribe { openSearch(it) })
    }

    private fun openSearch(searchText: String) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val bundle = Bundle()
        bundle.putString("search", searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    private fun openMovieDetails(movie: Movie) {
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        val bundle = Bundle()
        bundle.putString("title", movie.title)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun getPopularMovies() {
        compositeDisposable.add(MovieApiClient.apiClient.getPopularMovies(language = language)
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
                val popularMovies = listOf(
                    MainCardContainer(
                        R.string.popular,
                        it
                            .filter { movie -> movie.title != null }
                            .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                    )
                )
                movies_recycler_view.adapter = adapter.apply { addAll(popularMovies) }
            }, {
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
            })
        )
    }

    private fun getNowPlayingMovies() {
        compositeDisposable.add(MovieApiClient.apiClient.getNowPlayingMovies(language = language)
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
                val nowPlayingMovies = listOf(
                    MainCardContainer(
                        R.string.now_playing,
                        it
                            .filter { movie -> movie.title != null }
                            .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                    )
                )
                movies_recycler_view.adapter = adapter.apply { addAll(nowPlayingMovies) }
            }, {
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
            })
        )
    }

    private fun getUpcomingMovies() {
        compositeDisposable.add(MovieApiClient.apiClient.getUpcomingMovies(language = language)
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
                val upcomingMovies = listOf(
                    MainCardContainer(
                        R.string.upcoming,
                        it
                            .filter { movie -> movie.title != null }
                            .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                    )
                )
                movies_recycler_view.adapter = adapter.apply { addAll(upcomingMovies) }
            }, {
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
            })
        )
    }

    private fun getTopRatedMovies() {
        compositeDisposable.add(MovieApiClient.apiClient.getTopRatedMovies(language = language)
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
                val topRatedMovies = listOf(
                    MainCardContainer(
                        R.string.top_rated,
                        it
                            .filter { movie -> movie.title != null }
                            .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                    )
                )
                movies_recycler_view.adapter = adapter.apply { addAll(topRatedMovies) }
            }, {
                Timber.tag("TEST_OF_LOADING_DATA").d(it.toString())
            })
        )
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        compositeDisposable.clear()
        adapter.clear()
    }
}