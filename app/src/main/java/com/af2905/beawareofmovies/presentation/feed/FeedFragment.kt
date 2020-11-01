package com.af2905.beawareofmovies.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.af2905.beawareofmovies.Constants.MOVIE_ID
import com.af2905.beawareofmovies.Constants.SEARCH_QUERY
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.usecase.FeedUseCase
import com.af2905.beawareofmovies.util.extensions.applySchedulers
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.feed_fragment.*
import kotlinx.android.synthetic.main.feed_header.*
import java.util.concurrent.TimeUnit

class FeedFragment : Fragment() {
    private var compositeDisposable = CompositeDisposable()
    private lateinit var language: String
    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }
    lateinit var viewModel: FeedViewModel

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
        val feedUseCase = FeedUseCase()
        language = resources.getString(R.string.language)
        viewModel = ViewModelProvider(
            this, ViewModelFactory(feedUseCase, language)
        ).get(FeedViewModel::class.java)
        progress_bar.visibility = View.VISIBLE
        movies_recycler_view.layoutManager = LinearLayoutManager(context)
        getSearchQuery()
        viewModel.downloadMovies()
        viewModel.zippedMovies.observe(viewLifecycleOwner, {
            adapter.clear()
            showMovies(it)
        })
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

    private fun showMovies(zippedMovies: HashMap<FeedUseCase.MovieCategories, List<MovieVo>>) {
        val moviesContainer = mutableListOf<MainCardContainer>()

        val popular = zippedMovies[FeedUseCase.MovieCategories.POPULAR]
        popular?.let { moviesContainer.add(createContainer(R.string.popular, popular)) }

        val topRated = zippedMovies[FeedUseCase.MovieCategories.TOP_RATED]
        topRated?.let { moviesContainer.add(createContainer(R.string.top_rated, topRated)) }

        val nowPlaying = zippedMovies[FeedUseCase.MovieCategories.NOW_PLAYING]
        nowPlaying?.let { moviesContainer.add(createContainer(R.string.now_playing, nowPlaying)) }

        val upcoming = zippedMovies[FeedUseCase.MovieCategories.UPCOMING]
        upcoming?.let { moviesContainer.add(createContainer(R.string.upcoming, upcoming)) }

        progress_bar.visibility = View.GONE
        movies_recycler_view.adapter = adapter.apply { addAll(moviesContainer) }
    }

    private fun createContainer(title: Int, movies: List<MovieVo>): MainCardContainer {
        return MainCardContainer(
            title, movies.map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
        )
    }

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        compositeDisposable.clear()
        adapter.clear()
    }
}