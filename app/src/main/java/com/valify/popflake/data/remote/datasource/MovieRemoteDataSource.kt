package com.valify.popflake.data.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.valify.popflake.BuildConfig
import com.valify.popflake.data.remote.api.MovieApiService
import com.valify.popflake.data.remote.dto.MovieDto
import com.valify.popflake.data.remote.dto.MovieResponseDto
import com.valify.popflake.data.util.NetworkHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val api: MovieApiService,
    private val networkHandler: NetworkHandler
) {
    suspend fun getNowPlayingMovies(): Result<MovieResponseDto> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                Result.success(api.getNowPlayingMovies(BuildConfig.API_KEY))
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun getUpcomingMovies(): Result<MovieResponseDto> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                Result.success(api.getUpcomingMovies(BuildConfig.API_KEY))
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun getTopRatedMovies(): Result<MovieResponseDto> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                Result.success(api.getTopRatedMovies(BuildConfig.API_KEY))
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    suspend fun getPopularMovies(): Result<MovieResponseDto> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                Result.success(api.getPopularMovies(BuildConfig.API_KEY))
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }

    fun searchMovies(query: String): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(api, query, BuildConfig.API_KEY)
            }
        ).flow
    }

    suspend fun getMovieVideos(movieId: Int): Result<List<String>> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val response = api.getMovieVideos(movieId, BuildConfig.API_KEY)
                val trailerKeys = response.results
                    .filter { it.site.equals("youtube", true) && it.type.equals("trailer", true) }
                    .map { it.key }

                Result.success(trailerKeys)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(Exception("No internet connection"))
        }
    }
}