package com.valify.popflake.domain.usecase

import com.valify.popflake.domain.model.Movie
import com.valify.popflake.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetTopRatedMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(movieRepository)
    }

    @Test
    fun `invoke returns success result with movies`() = runBlocking {
        // Arrange
        val movies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "Overview 1", posterPath = "/path1.jpg",
                backdropPath = "/backdrop1.jpg", releaseDate = "2023-01-01", voteAverage = 9.5),
            Movie(id = 2, title = "Movie 2", overview = "Overview 2", posterPath = "/path2.jpg",
                backdropPath = "/backdrop2.jpg", releaseDate = "2023-01-02", voteAverage = 9.2)
        )
        coEvery { movieRepository.getTopRatedMovies(any()) } returns flowOf(Result.success(movies))

        // Act
        val result = getTopRatedMoviesUseCase(forceRefresh = false).first()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(movies, result.getOrNull())
    }

    @Test
    fun `invoke returns error result when repository fails`() = runBlocking {
        // Arrange
        val exception = Exception("Network error")
        coEvery { movieRepository.getTopRatedMovies(any()) } returns flowOf(Result.failure(exception))

        // Act
        val result = getTopRatedMoviesUseCase(forceRefresh = true).first()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}