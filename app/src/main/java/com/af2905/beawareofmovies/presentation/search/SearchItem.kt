package com.af2905.beawareofmovies.presentation.search

import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.network.PicassoClient
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.util.extensions.getYearFromReleaseDate
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_search.*

class SearchItem(private val content: MovieVo, private val onClick: (movie: MovieVo) -> Unit) : Item() {

    override fun getLayout() = R.layout.item_search

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.title_text_view.text = content.title
        viewHolder.movie_rating.rating = content.voteAverage.toFloat()
        viewHolder.overview_text_view.text = content.overview
        viewHolder.release_date_text_view.text = content.releaseDate?.getYearFromReleaseDate()
        content.posterPath?.let { PicassoClient.downloadImage(it, viewHolder.image_preview) }
        viewHolder.content.setOnClickListener { onClick.invoke(content) }
    }
}