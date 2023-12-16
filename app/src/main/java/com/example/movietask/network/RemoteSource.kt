package com.example.movietask.network

import com.example.movietask.model.MoviesResponse
interface RemoteSource {
    suspend fun getMovies(): MoviesResponse

}