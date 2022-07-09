package com.david.haru.myandroidtemplate.ui.main

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.david.haru.myandroidtemplate.network.MovieItem
import com.david.haru.myandroidtemplate.repo.FeedPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val pagingSource :FeedPagingSource,
) : ViewModel() {

    private var _feed: Flow<PagingData<MovieItem>>? = null
    val feed get() = _feed!!
    init {
        fetchFeed()
    }

    private fun fetchFeed() {
        _feed = Pager(PagingConfig(pageSize = 10)) {
            pagingSource
        }.flow
    }
}