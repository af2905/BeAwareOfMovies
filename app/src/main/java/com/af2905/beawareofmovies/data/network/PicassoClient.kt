package com.af2905.beawareofmovies.data.network

import android.widget.ImageView
import com.squareup.picasso.Picasso

object PicassoClient {
    fun downloadImage(imageUrl: String, imageView: ImageView) {
        Picasso.get().load(imageUrl).into(imageView)
    }
}


