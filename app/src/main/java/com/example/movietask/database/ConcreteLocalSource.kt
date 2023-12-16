package com.example.movietask.database

import com.example.movietask.model.ResultsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConcreteLocalSource  @Inject constructor (private val dao: MoviesDao) : LocalSource  {

    override suspend fun insertMovie(movie: ResultsItem) {
        dao.insertMovie(movie)
    }

    override suspend  fun deleteAllMovies() {
    dao.deleteAllMovies()    }

    override  fun getAllMovies(): Flow<List<ResultsItem>> {
        return dao.getAllMovies()     }

    override suspend fun insertAll(movies: List<ResultsItem>) {
        dao.insertAll(movies)
    }
}