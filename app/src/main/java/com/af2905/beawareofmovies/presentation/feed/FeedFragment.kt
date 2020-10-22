package com.af2905.beawareofmovies.presentation.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.af2905.beawareofmovies.Constants.SEARCH_QUERY
import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.database.MovieDatabase
import com.af2905.beawareofmovies.data.repository.RemoteRepository
import com.af2905.beawareofmovies.data.vo.MovieVo
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
        //downloadMovies()
        testDownloadMovies()
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
        //bundle.putInt(MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun testDownloadMovies() {
        val database = MovieDatabase.get(requireContext())
        val repository = RemoteRepository(database)
        compositeDisposable.add(repository.getMovies().map {
            MainCardContainer(
                R.string.popular, it.filter { movie -> movie.title != null }
                    .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
            )
        }
            .applySchedulers()
            .subscribe(
                {
                    movies_recycler_view.adapter = adapter.apply { add(it) }
                },
                {}
            ))
    }


    /*   private fun downloadMovies() {
           val apiClient = MovieApiClient.apiClient
           compositeDisposable.add(Single.zip(
               apiClient.getPopularMovies(language = language),
               apiClient.getNowPlayingMovies(language = language),
               apiClient.getUpcomingMovies(language = language),
               apiClient.getTopRatedMovies(language = language),
               Function4 { t1, t2, t3, t4 ->
                   return@Function4 listOf(
                       MainCardContainer(
                           R.string.popular, t1.results?.filter { movie -> movie.title != null }
                               ?.map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                       ),
                       MainCardContainer(
                           R.string.now_playing, t2.results?.filter { movie -> movie.title != null }
                               .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                       ),
                       MainCardContainer(
                           R.string.upcoming, t3.results?.filter { movie -> movie.title != null }
                               .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                       ),
                       MainCardContainer(
                           R.string.top_rated, t4.results?.filter { movie -> movie.title != null }
                               .map { movie -> MovieItem(movie) { openMovieDetails(movie) } }.toList()
                       )
                   )
               }
           )
               .applySchedulers()
               .subscribe({
                   movies_recycler_view.adapter = adapter.apply { addAll(it) }
               }, {

               })
           )
       }*/

    override fun onStop() {
        super.onStop()
        search_toolbar.clear()
        compositeDisposable.clear()
        adapter.clear()
    }
}