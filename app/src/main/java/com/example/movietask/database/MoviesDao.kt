package com.example.movietask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movietask.model.ResultsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: ResultsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<ResultsItem>)

    @Query("DELETE FROM movies")
   suspend  fun deleteAllMovies():Int


    @Query("SELECT * FROM movies")
     fun getAllMovies(): Flow<List<ResultsItem>>
}