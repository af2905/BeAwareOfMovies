package com.af2905.beawareofmovies.ui.search

import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.Movie
import com.af2905.beawareofmovies.network.PicassoClient
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_search.*

class SearchItem(private val content: Movie, private val onClick: (movie: Movie) -> Unit) : Item() {

    override fun getLayout() = R.layout.item_search

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.title_text_view.text = content.title
        viewHolder.movie_rating.rating = content.voteAverage.toFloat()
        viewHolder.overview_text_view.text = content.overview
        val releaseTitle = viewHolder.itemView.context.getString(R.string.release_date)
        val releaseTitleWithDate = "$releaseTitle ${content.releaseDate}"
        viewHolder.release_date_text_view.text = releaseTitleWithDate
        content.posterPath?.let { PicassoClient.downloadImage(it, viewHolder.image_preview) }
        viewHolder.content.setOnClickListener { onClick.invoke(content) }
    }
}