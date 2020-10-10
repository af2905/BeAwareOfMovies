package com.af2905.beawareofmovies.ui

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

fun <T> Observable<T>.observeOnMainThread(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}