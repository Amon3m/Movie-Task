package com.example.movietask.network

import com.example.movietask.model.DetailsResponse
import com.example.movietask.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
private const val API_KEY= "c50f5aa4e7c95a2a553d29b81aad6dd0"

interface ApiService {
    @GET("3/trending/movie/day")
    suspend fun getMovies(
        @Query("api_key") key: String=API_KEY,
    ): MoviesResponse
    @GET("3/trending/movie/day/{id}")
    suspend fun getMovieById(
        @Query("api_key") key: String=API_KEY,@Path("id") id: Int
    ): DetailsResponse
}