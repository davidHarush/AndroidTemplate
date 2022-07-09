package com.david.haru.myandroidtemplate.repo


import com.david.haru.myandroidtemplate.network.IMoviesApiService
import com.david.haru.myandroidtemplate.network.Movies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepo @Inject constructor(
    private val webService: IMoviesApiService
) {

   val movies: Flow<Movies> = flow {
        val movies = webService.getPopularMoviesApi()
        emit(movies)
    }

}