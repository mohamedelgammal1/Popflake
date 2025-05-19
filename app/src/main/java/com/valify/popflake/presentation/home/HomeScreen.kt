package com.valify.popflake.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.valify.popflake.R
import com.valify.popflake.domain.model.Movie
import com.valify.popflake.presentation.common.components.ErrorView
import com.valify.popflake.presentation.common.components.LoadingView
import com.valify.popflake.presentation.common.components.MovieItem
import com.valify.popflake.presentation.common.state.UiState
import com.valify.popflake.presentation.home.components.BannerSlider
import com.valify.popflake.presentation.home.components.HorizontalMovieList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val nowPlayingState by viewModel.nowPlayingMovies.collectAsStateWithLifecycle()
    val upcomingState by viewModel.upcomingMovies.collectAsStateWithLifecycle()
    val topRatedState by viewModel.topRatedMovies.collectAsStateWithLifecycle()
    val popularState by viewModel.popularMovies.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refreshAllData() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Banner Slider
            item {
                when (nowPlayingState) {
                    is UiState.Success -> {
                        BannerSlider(
                            movies = (nowPlayingState as UiState.Success<List<Movie>>).data.take(5),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = (nowPlayingState as UiState.Error).message,
                            onRetry = { viewModel.refreshNowPlaying() },
                        )
                    }
                }
            }

            // Upcoming Movies
            item {
                Text(
                    text = stringResource(R.string.upcoming_movies),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                when (upcomingState) {
                    is UiState.Success -> {
                        // todo fix
                        HorizontalMovieList(
                            movies = (upcomingState as UiState.Success<List<Movie>>).data,
                            onMovieClick = { /* Handle movie click */TODO() },
                            title = TODO(),
                            modifier = TODO()
                        )
                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = (upcomingState as UiState.Error).message,
                            onRetry = { viewModel.refreshUpcoming() },

                        )
                    }
                }
            }

            // Now Playing Movies
            item {
                Text(
                    text = stringResource(R.string.now_playing),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                when (nowPlayingState) {
                    is UiState.Success -> {
                        // todo fix
                        HorizontalMovieList(
                            movies = (nowPlayingState as UiState.Success<List<Movie>>).data,
                            onMovieClick = { /* Handle movie click */TODO() },
                            title = TODO(),
                            modifier = TODO()
                        )
                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = (nowPlayingState as UiState.Error).message,
                            onRetry = { viewModel.refreshNowPlaying() },
                        )
                    }
                }
            }

            // Top Rated Movies
            item {
                Text(
                    text = stringResource(R.string.top_rated_movies),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                when (topRatedState) {
                    is UiState.Success -> {
                        // todo fix
                        HorizontalMovieList(
                            movies = (topRatedState as UiState.Success<List<Movie>>).data,
                            onMovieClick = { /* Handle movie click */TODO() },
                            title = TODO(),
                            modifier = TODO()
                        )
                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = (topRatedState as UiState.Error).message,
                            onRetry = { viewModel.refreshTopRated() },
                        )
                    }
                }
            }

            // Popular Movies (Grid)
            item {
                Text(
                    text = stringResource(R.string.popular_movies),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                when (popularState) {
                    is UiState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp)
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items((popularState as UiState.Success<List<Movie>>).data.take(10)) { movie ->
                                MovieItem(
                                    movie = movie,
                                    onMovieClick = { /* Handle movie click */ }
                                )
                            }
                        }
                    }
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Error -> {
                        ErrorView(
                            message = (popularState as UiState.Error).message,
                            onRetry = { viewModel.refreshPopular() },
                        )
                    }
                }
            }
        }
    }
}