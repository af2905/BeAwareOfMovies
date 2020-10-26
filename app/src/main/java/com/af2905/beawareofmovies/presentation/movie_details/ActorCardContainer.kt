package com.af2905.beawareofmovies.presentation.movie_details

import androidx.annotation.StringRes
import com.af2905.beawareofmovies.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card_actors.*


class ActorCardContainer (@StringRes private val title: Int, private val items: List<Item>) : Item() {

    override fun getLayout() = R.layout.item_card_actors

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.title_text_view.text = viewHolder.title_text_view.context.getString(title)
        viewHolder.actors_container.adapter =
            GroupAdapter<GroupieViewHolder>().apply { addAll(items) }
    }
}