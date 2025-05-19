package com.valify.popflake.domain.usecase


import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.paging.PagingData
import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.repository.MovieRepository


class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Movie>> {
        return movieRepository.searchMovies(query)
    }
}