package com.example.movietask.network

import com.example.movietask.model.MoviesResponse
import javax.inject.Inject

class ApiClient @Inject constructor(private val apiService: ApiService) :
    RemoteSource {
    override suspend fun getMovies(): MoviesResponse = apiService.getMovies()

}