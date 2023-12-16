package com.example.movietask.model

import kotlinx.coroutines.flow.Flow

interface RepoInterface {
    suspend fun getMovies(): Flow<MoviesResponse>
    suspend fun getMovieByID(id :Int):  Flow<DetailsResponse>
     fun getAllMoviesFromDataBase(): Flow<List<ResultsItem>>
    suspend fun insertMovieToDatabase(moviesEntity: ResultsItem)

    suspend fun insertAllMoviesToDatabase(movies: List<ResultsItem>)

    suspend fun deleteMoviesFromDataBase()
    suspend fun getMoviesLocal(): Flow<MoviesResponse>
}