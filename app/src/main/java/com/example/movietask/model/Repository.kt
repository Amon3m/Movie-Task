package com.example.movietask.model

import com.example.movietask.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteSource: RemoteSource,

    ) : RepoInterface {
    override suspend fun getMovies(): Flow<MoviesResponse> {
        return flowOf(remoteSource.getMovies())

    }

    override suspend fun getMovieByID(id: Int): Flow<DetailsResponse> {
        return flowOf(remoteSource.getMovieByID(id))
    }
}
