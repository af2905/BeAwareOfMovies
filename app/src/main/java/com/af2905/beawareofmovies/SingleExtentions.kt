package com.af2905.beawareofmovies

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Single<T>.observeOnMainThread(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}