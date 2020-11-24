package com.af2905.beawareofmovies.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.af2905.beawareofmovies.domain.usecase.FeedUseCase

class ViewModelFactory(private val feedUseCase: FeedUseCase, private val language: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(feedUseCase, language) as T
    }
}