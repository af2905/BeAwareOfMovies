package com.af2905.beawareofmovies.presentation.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.usecase.FeedUseCase
import io.reactivex.disposables.Disposables

class FeedViewModel(private val feedUseCase: FeedUseCase, private val language: String) :
    ViewModel() {
    private var requestDisposable = Disposables.empty()
    val zippedMovies =
        MutableLiveData<HashMap<FeedUseCase.MovieCategories, List<MovieVo>>>()

    fun downloadMovies() {
        requestDisposable.dispose()
        requestDisposable = feedUseCase
            .getZippedMovies(language)
            .subscribe({
                zippedMovies.value = it
            }, {

            })
    }

    override fun onCleared() {
        super.onCleared()
        requestDisposable.dispose()
    }
}




