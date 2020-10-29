package com.af2905.beawareofmovies.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.af2905.beawareofmovies.data.vo.MovieVo
import com.af2905.beawareofmovies.domain.usecase.FeedUseCase
import io.reactivex.disposables.Disposables

class FeedViewModel() : ViewModel() {
    private var requestDisposable = Disposables.empty()
    private val zippedMovies =
        MutableLiveData<HashMap<FeedUseCase.MovieCategories, List<MovieVo>>>()

    fun downloadMovies(feedUseCase: FeedUseCase, language: String) {
        requestDisposable.dispose()
        requestDisposable = feedUseCase
            .getZippedMovies(language)
            .subscribe({
                zippedMovies.value = it
            }, {

            })
    }

    fun getZippedMovies(): LiveData<HashMap<FeedUseCase.MovieCategories, List<MovieVo>>> {
        return zippedMovies
    }

    override fun onCleared() {
        super.onCleared()
        requestDisposable.dispose()
    }
}




