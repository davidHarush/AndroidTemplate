package com.david.haru.myandroidtemplate.network

import retrofit2.http.GET

interface IMoviesApiService {

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/"
    }

    @GET("/3/movie/popular?api_key=56a778f90174e0061b6e7c69a5e3c9f2")
    suspend fun getPopularMoviesApi(): Movies
}