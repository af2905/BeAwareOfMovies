package com.af2905.beawareofmovies.presentation.movie_details

import com.af2905.beawareofmovies.R
import com.af2905.beawareofmovies.data.network.PicassoClient
import com.af2905.beawareofmovies.data.vo.MovieActorVo
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor_with_text.*


class ActorItem(private val content: MovieActorVo) : Item() {

    override fun getLayout() = R.layout.item_actor_with_text

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        content.profilePath?.let { PicassoClient.downloadImage(it, viewHolder.actor_image_preview) }
        viewHolder.actor_name.text = content.name
    }
}