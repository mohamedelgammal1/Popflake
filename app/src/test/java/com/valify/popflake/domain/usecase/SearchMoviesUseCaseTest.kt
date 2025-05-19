package com.valify.popflake.domain.usecase

import androidx.paging.PagingData
import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        searchMoviesUseCase = SearchMoviesUseCase(movieRepository)
    }

    @Test
    fun `invoke calls repository searchMovies with correct query`() = runBlocking {
        // Arrange
        val query = "Avengers"
        val pagingDataFlow: Flow<PagingData<Movie>> = flowOf(PagingData.empty())
        every { movieRepository.searchMovies(query) } returns pagingDataFlow

        // Act
        val result = searchMoviesUseCase(query)

        // Assert
        assertEquals(pagingDataFlow, result)
        verify { movieRepository.searchMovies(query) }
    }

    @Test
    fun `invoke with empty query returns empty result`() = runBlocking {
        // Arrange
        val query = ""
        val pagingDataFlow: Flow<PagingData<Movie>> = flowOf(PagingData.empty())
        every { movieRepository.searchMovies(query) } returns pagingDataFlow

        // Act
        val result = searchMoviesUseCase(query)

        // Assert
        assertEquals(pagingDataFlow, result)
        verify { movieRepository.searchMovies(query) }
    }
}