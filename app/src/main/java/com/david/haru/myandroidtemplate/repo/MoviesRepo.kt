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

//    suspend fun getMovies(): SimpleResponse<Movie.ApiResult> {
//        return safeApiCall { webService.getPopularMovies() }
//    }

    val latestNews: Flow<Movies> = flow {
        val latestNews = webService.getPopularMoviesApi()
        emit(latestNews)
    }


//    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
//        return try {
//            SimpleResponse.success(apiCall.invoke())
//        } catch (e: Exception) {
//            SimpleResponse.failure(e)
//        }
//    }


}