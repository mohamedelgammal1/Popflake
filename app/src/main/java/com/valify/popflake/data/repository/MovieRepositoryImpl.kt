package com.valify.popflake.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.valify.popflake.data.remote.datasource.MovieRemoteDataSource
import com.valify.popflake.data.remote.dto.toDomainModel
import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override fun getNowPlayingMovies(forceRefresh: Boolean): Flow<Result<List<Movie>>> {
        return kotlinx.coroutines.flow.flow {
            try {
                val result = remoteDataSource.getNowPlayingMovies()
                emit(result.map { response ->
                    response.results.map { it.toDomainModel() }
                })
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override fun getUpcomingMovies(forceRefresh: Boolean): Flow<Result<List<Movie>>> {
        return kotlinx.coroutines.flow.flow {
            try {
                val result = remoteDataSource.getUpcomingMovies()
                emit(result.map { response ->
                    response.results.map { it.toDomainModel() }
                })
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override fun getTopRatedMovies(forceRefresh: Boolean): Flow<Result<List<Movie>>> {
        return kotlinx.coroutines.flow.flow {
            try {
                val result = remoteDataSource.getTopRatedMovies()
                emit(result.map { response ->
                    response.results.map { it.toDomainModel() }
                })
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override fun getPopularMovies(limit: Int, forceRefresh: Boolean): Flow<Result<List<Movie>>> {
        return kotlinx.coroutines.flow.flow {
            try {
                val result = remoteDataSource.getPopularMovies()
                emit(result.map { response ->
                    response.results.take(limit).map { it.toDomainModel() }
                })
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return remoteDataSource.searchMovies(query).map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }

    override suspend fun getMovieTrailers(movieId: Int): Result<List<String>> {
        return remoteDataSource.getMovieVideos(movieId)
    }
}