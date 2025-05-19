package com.valify.popflake.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.valify.popflake.data.remote.api.MovieApiService
import com.valify.popflake.data.remote.dto.MovieDto
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val api: MovieApiService,
    private val query: String,
    private val apiKey: String
) : PagingSource<Int, MovieDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val page = params.key ?: 1

        return try {
            val response = api.searchMovies(apiKey, query, page)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(Exception("Please check your internet connection"))
        } catch (e: HttpException) {
            LoadResult.Error(Exception("An error occurred while fetching data"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}