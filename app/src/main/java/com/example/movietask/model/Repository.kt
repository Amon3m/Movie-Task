package com.example.movietask.model

import android.util.Log
import com.example.movietask.database.LocalSource

import com.example.movietask.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteSource: RemoteSource,
    var concreteLocalSource: LocalSource
) : RepoInterface {
    override suspend fun getMovies(): Flow<MoviesResponse> {
        return flowOf(remoteSource.getMovies())

    }

    override suspend fun getMovieByID(id: Int): Flow<DetailsResponse> {
        return flowOf(remoteSource.getMovieByID(id))
    }

    override fun getAllMoviesFromDataBase(): Flow<List<ResultsItem>> {
        return concreteLocalSource.getAllMovies()

    }

    override suspend fun insertMovieToDatabase(resultsItem: ResultsItem) {
        return concreteLocalSource.insertMovie(resultsItem)
    }

    override suspend fun insertAllMoviesToDatabase(movies: List<ResultsItem>) {
        return concreteLocalSource.insertAll(movies)
    }


    override suspend fun deleteMoviesFromDataBase() {
        return concreteLocalSource.deleteAllMovies()
    }

    override suspend fun getMoviesLocal(): Flow<MoviesResponse> = flow {
        getAllMoviesFromDataBase().collect {
            if (it.isNotEmpty()) {
                emit(MoviesResponse(results = it))
                Log.e("myDatabase isNotEmpty", "$it")
            } else {
                getMovies().collect {
                    val data = it.results ?: emptyList()

                    insertAllMoviesToDatabase(data as List<ResultsItem>)
                    getAllMoviesFromDataBase().collect {
                        emit(MoviesResponse(results = it))
                        Log.e("myDatabase else", "$it")

                    }
                }
            }

        }
    }
}
