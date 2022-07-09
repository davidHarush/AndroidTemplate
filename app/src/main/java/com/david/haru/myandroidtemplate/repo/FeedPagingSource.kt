package com.david.haru.myandroidtemplate.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.david.haru.myandroidtemplate.network.MovieItem
import javax.inject.Inject


class FeedPagingSource @Inject constructor(
    private val movieRepo: MoviesRepo
) : PagingSource<Int, MovieItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val page = params.key ?: STARTING_PAGE_INDEX

        val pageNumber = params.key ?: 1
        val response = movieRepo.getMovies(page)
        val feed = response.results
        val hasMore = pageNumber < response.total_pages

        return try {
            if(hasMore) {
                val prevKey = if (pageNumber == 1) null else pageNumber - 1
                LoadResult.Page(
                    data = feed,
                    prevKey = prevKey,
                    nextKey = pageNumber +1
                )
            }else{
                LoadResult.Page(
                    data = feed,
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}