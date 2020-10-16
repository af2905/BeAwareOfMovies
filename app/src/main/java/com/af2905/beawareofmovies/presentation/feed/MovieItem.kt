package com.af2905.beawareofmovies.presentation.feed

import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.repository.database.dto.Movie
import com.af2905.beawareofmovies.data.repository.network.PicassoClient
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_with_text.*

class MovieItem(private val content: Movie, private val onClick: (movie: Movie) -> Unit) : Item() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.description.text = content.title
        viewHolder.movie_rating.rating = content.voteAverage.toFloat()
        content.posterPath?.let { PicassoClient.downloadImage(it, viewHolder.image_preview) }
        viewHolder.content.setOnClickListener { onClick.invoke(content) }
    }
}