package com.af2905.beawareofmovies.util.extensions

fun String.getYearFromReleaseDate(): String = substringBefore("-")

fun String?.getFullPathToImage(): String? {
    if (this == null) return null
    return "https://image.tmdb.org/t/p/w500/$this"
}