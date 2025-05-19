package com.valify.popflake.domain.repository

import androidx.paging.PagingData
import com.valify.popflake.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlayingMovies(forceRefresh: Boolean = false): Flow<Result<List<Movie>>>
    fun getUpcomingMovies(forceRefresh: Boolean = false): Flow<Result<List<Movie>>>
    fun getTopRatedMovies(forceRefresh: Boolean = false): Flow<Result<List<Movie>>>
    fun getPopularMovies(limit: Int = 10, forceRefresh: Boolean = false): Flow<Result<List<Movie>>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    suspend fun getMovieTrailers(movieId: Int): Result<List<String>>
}