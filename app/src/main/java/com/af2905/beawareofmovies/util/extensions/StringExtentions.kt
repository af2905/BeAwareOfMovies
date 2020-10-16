package com.af2905.beawareofmovies.util.extensions

fun String.getYearFromReleaseDate(): String {
    return substringBefore("-")
}