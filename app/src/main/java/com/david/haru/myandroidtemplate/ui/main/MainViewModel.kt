package com.david.haru.myandroidtemplate.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.haru.myandroidtemplate.network.Movies
import com.david.haru.myandroidtemplate.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepo: MoviesRepo
) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            movieRepo.movies
                .catch { exception ->
                    _uiState.emit(UiState.Error(exception))
                }
                .collect { movies ->
                    _uiState.emit(UiState.Success(movies))
                }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val movies: Movies): UiState()
    data class Error(val exception: Throwable): UiState()
}