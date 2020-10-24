package com.af2905.beawareofmovies.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.af2905.beawareofmovies.Constants.MOVIE_ID
import com.af2905.beawareofmovies.Constants.SEARCH_QUERY
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.repository.remote_repository.NowPlayingRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.PopularRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.TopRatedRemoteRepository
import com.af2905.beawareofmovies.data.repository.remote_repository.UpcomingRemoteRepository
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
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
        progress_bar.visibility = View.VISIBLE
        language = resources.getString(R.string.language)
        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        getSearchQuery()
        downloadMovies()
    }

    private fun getSearchQuery() {
        compositeDisposable.add(search_toolbar.onTextChangedObservable
            .map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .filter { it.length > 2 }
            .applySchedulers()
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
        bundle.putString(SEARCH_QUERY, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
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
        bundle.putInt(MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun downloadMovies() {
        compositeDisposable.add(
            Observable.zip(
                PopularRemoteRepository(language = language).getMovies(),
                TopRatedRemoteRepository(language = language).getMovies(),
                NowPlayingRemoteRepository(language = language).getMovies(),
                UpcomingRemoteRepository(language = language).getMovies(),
                Function4 { t1: List<MovieVo>, t2: List<MovieVo>, t3: List<MovieVo>, t4: List<MovieVo>
                    ->
                    return@Function4 listOf(
                        MainCardContainer(
                            R.string.popular, t1.filter { movie -> movie.title != null }
                                .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }
                                .toList()
                        ),
                        MainCardContainer(
                            R.string.top_rated,
                            t2.filter { movie -> movie.title != null }
                                .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }
                                .toList()
                        ),
                        MainCardContainer(
                            R.string.now_playing,
                            t3.filter { movie -> movie.title != null }
                                .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }
                                .toList()
                        ),
                        MainCardContainer(
                            R.string.upcoming,
                            t4.filter { movie -> movie.title != null }
                                .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }
                                .toList()
                        ),
                    )
                }
            )
                .applySchedulers()
                .subscribe({
                    progress_bar.visibility = View.GONE
                    movies_recycler_view.adapter = adapter.apply { addAll(it) }
                }, {

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