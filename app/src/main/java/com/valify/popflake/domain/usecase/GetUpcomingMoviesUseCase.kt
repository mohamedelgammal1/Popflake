package com.valify.popflake.domain.usecase

import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<Result<List<Movie>>> {
        return movieRepository.getUpcomingMovies(forceRefresh)
    }
}