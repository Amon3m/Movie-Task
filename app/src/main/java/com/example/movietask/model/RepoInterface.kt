package com.example.movietask.model

import kotlinx.coroutines.flow.Flow

interface RepoInterface {
    suspend fun getMovies(): Flow<MoviesResponse>
    suspend fun getMovieByID(id :Int):  Flow<DetailsResponse>

}