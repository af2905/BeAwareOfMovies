package com.af2905.beawareofmovies.ui.extensions

fun String.getYearFromReleaseDate(): String {
    return substringBefore("-")
}