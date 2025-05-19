package com.valify.popflake.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.usecase.GetNowPlayingMoviesUseCase
import com.valify.popflake.domain.usecase.GetPopularMoviesUseCase
import com.valify.popflake.domain.usecase.GetTopRatedMoviesUseCase
import com.valify.popflake.domain.usecase.GetUpcomingMoviesUseCase
import com.valify.popflake.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val nowPlayingMovies: StateFlow<UiState<List<Movie>>> = _nowPlayingMovies.asStateFlow()

    private val _upcomingMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val upcomingMovies: StateFlow<UiState<List<Movie>>> = _upcomingMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val topRatedMovies: StateFlow<UiState<List<Movie>>> = _topRatedMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val popularMovies: StateFlow<UiState<List<Movie>>> = _popularMovies.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadAllData()
    }

    private fun loadAllData() {
        loadNowPlayingMovies()
        loadUpcomingMovies()
        loadTopRatedMovies()
        loadPopularMovies()
    }

    private fun loadNowPlayingMovies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase(forceRefresh)
                .collect { result ->
                    _nowPlayingMovies.value = when {
                        result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                        result.isFailure -> UiState.Error(
                            result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        )
                        else -> UiState.Loading
                    }
                }
        }
    }

    private fun loadUpcomingMovies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getUpcomingMoviesUseCase(forceRefresh)
                .collect { result ->
                    _upcomingMovies.value = when {
                        result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                        result.isFailure -> UiState.Error(
                            result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        )
                        else -> UiState.Loading
                    }
                }
        }
    }

    private fun loadTopRatedMovies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getTopRatedMoviesUseCase(forceRefresh)
                .collect { result ->
                    _topRatedMovies.value = when {
                        result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                        result.isFailure -> UiState.Error(
                            result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        )
                        else -> UiState.Loading
                    }
                }
        }
    }

    private fun loadPopularMovies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getPopularMoviesUseCase(10, forceRefresh)
                .collect { result ->
                    _popularMovies.value = when {
                        result.isSuccess -> UiState.Success(result.getOrNull() ?: emptyList())
                        result.isFailure -> UiState.Error(
                            result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        )
                        else -> UiState.Loading
                    }
                }
        }
    }

    fun refreshAllData() {
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                loadAllData()
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun refreshNowPlaying() {
        _nowPlayingMovies.value = UiState.Loading
        loadNowPlayingMovies(true)
    }

    fun refreshUpcoming() {
        _upcomingMovies.value = UiState.Loading
        loadUpcomingMovies(true)
    }

    fun refreshTopRated() {
        _topRatedMovies.value = UiState.Loading
        loadTopRatedMovies(true)
    }

    fun refreshPopular() {
        _popularMovies.value = UiState.Loading
        loadPopularMovies(true)
    }
}