package com.af2905.beawareofmovies.network

import android.widget.ImageView
import com.squareup.picasso.Picasso

object PicassoClient {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original/"

    fun downloadImage(imageUrl: String, imageView: ImageView) {
        Picasso.get().load(IMAGE_BASE_URL + imageUrl).into(imageView)
    }
}


