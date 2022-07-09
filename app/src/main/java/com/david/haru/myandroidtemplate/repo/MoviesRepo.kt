package com.david.haru.myandroidtemplate.repo


import com.david.haru.myandroidtemplate.network.IMoviesApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepo @Inject constructor(
    private val webService: IMoviesApiService
) {
    suspend fun getMovies(page: Int) = webService.getPopularMoviesApi(page = page)
}