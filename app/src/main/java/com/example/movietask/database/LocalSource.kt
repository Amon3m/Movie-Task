package com.example.movietask.database

import com.example.movietask.model.ResultsItem
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insertMovie(movie: ResultsItem)
     suspend fun deleteAllMovies()
     fun getAllMovies(): Flow<List<ResultsItem>>
    suspend fun insertAll(movies: List<ResultsItem>)

}