package com.valify.popflake.domain.usecase

import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(limit: Int = 10, forceRefresh: Boolean = false): Flow<Result<List<Movie>>> {
        return movieRepository.getPopularMovies(limit, forceRefresh)
    }
}