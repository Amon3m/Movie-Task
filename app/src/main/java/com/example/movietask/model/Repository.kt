package com.example.movietask.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.movietask.database.LocalSource

import com.example.movietask.network.RemoteSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val FOUR_HOURS = 4 * 60 * 60 * 1000L

class Repository @Inject constructor(
    private val remoteSource: RemoteSource,
    private var concreteLocalSource: LocalSource,
    @ApplicationContext val context: Context
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

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

    override suspend fun insertAllMoviesToDatabase(movies: List<ResultsItem>) {
        return concreteLocalSource.insertAll(movies)
    }


    override suspend fun deleteMoviesFromDataBase() {
        return concreteLocalSource.deleteAllMovies()
    }

    override suspend fun getMoviesLocal(): Flow<MoviesResponse> = flow {
        val lastUpdateTime = getLastUpdateTimeFromDataStore()
        Log.e("myDatabase ", " lastUpdateTime $lastUpdateTime")

        if (System.currentTimeMillis() - lastUpdateTime > FOUR_HOURS && lastUpdateTime != 0L) { //4h update
            getMovies().collect {
                val data = it.results ?: emptyList()

                deleteMoviesFromDataBase()
                insertAllMoviesToDatabase(data as List<ResultsItem>)
                updateLastUpdateTimeInDataStore()

                getAllMoviesFromDataBase().collect {
                    emit(MoviesResponse(results = it))
                    Log.e("myDatabase ", "4h update $it")

                }
            }
        } else { //we are in the 4hr

            getAllMoviesFromDataBase().collect {
                if (it.isNotEmpty()) {//myDatabase is notEmpty
                    emit(MoviesResponse(results = it))

                    Log.e("myDatabase isNotEmpty", "$it")
                } else {//myDatabase isEmpty
                    getMovies().collect {
                        val data = it.results ?: emptyList()

                        updateLastUpdateTimeInDataStore()

                        insertAllMoviesToDatabase(data as List<ResultsItem>)
                        getAllMoviesFromDataBase().collect {
                            emit(MoviesResponse(results = it))
                            Log.e("myDatabase isEmpty", "$it")

                        }
                    }
                }

            }
        }
    }

    private suspend fun getLastUpdateTimeFromDataStore(): Long {
        return context.dataStore.data.map { preferences ->
            preferences[LastUpdateTimeKey.lastUpdateTime] ?: 0L
        }.first()
    }

    private suspend fun updateLastUpdateTimeInDataStore() {
        context.dataStore.edit { preferences ->
            preferences[LastUpdateTimeKey.lastUpdateTime] = System.currentTimeMillis()

            Log.e("myDatabase ", " updateLastUpdateTimeInDataStore ${System.currentTimeMillis()}")

        }
    }
}

object LastUpdateTimeKey {
    val lastUpdateTime: Preferences.Key<Long> = longPreferencesKey("last_update_time")
}
