package com.example.movietask.network

import com.example.movietask.model.DetailsResponse
import com.example.movietask.model.MoviesResponse
interface RemoteSource {
    suspend fun getMovies(): MoviesResponse
    suspend fun getMovieByID(id :Int): DetailsResponse


}