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

class GetNowPlayingMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(movieRepository)
    }

    @Test
    fun `invoke returns success result with movies`() = runBlocking {
        // Arrange
        val movies = listOf(
            Movie(id = 1, title = "Movie 1", overview = "Overview 1", posterPath = "/path1.jpg",
                backdropPath = "/backdrop1.jpg", releaseDate = "2023-01-01", voteAverage = 8.5),
            Movie(id = 2, title = "Movie 2", overview = "Overview 2", posterPath = "/path2.jpg",
                backdropPath = "/backdrop2.jpg", releaseDate = "2023-01-02", voteAverage = 7.5)
        )
        coEvery { movieRepository.getNowPlayingMovies(any()) } returns flowOf(Result.success(movies))

        // Act
        val result = getNowPlayingMoviesUseCase(forceRefresh = false).first()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(movies, result.getOrNull())
    }

    @Test
    fun `invoke returns error result when repository fails`() = runBlocking {
        // Arrange
        val exception = Exception("Network error")
        coEvery { movieRepository.getNowPlayingMovies(any()) } returns flowOf(Result.failure(exception))

        // Act
        val result = getNowPlayingMoviesUseCase(forceRefresh = true).first()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}