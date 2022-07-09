package com.david.haru.myandroidtemplate.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.haru.myandroidtemplate.network.Movies
import com.david.haru.myandroidtemplate.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepo: MoviesRepo
) : ViewModel() {

    private var _movies: MutableLiveData<Movies> = MutableLiveData()
    val movies get() = _movies

    private var _onErr: MutableLiveData<String> = MutableLiveData()
    val onErr get() = _onErr

    init {
        viewModelScope.launch {
            movieRepo.movies
                .catch { exception ->
                    _onErr.postValue(exception.message)
                }
                .collect { movie ->
                    _movies.postValue(movie)
                }
        }
    }
}