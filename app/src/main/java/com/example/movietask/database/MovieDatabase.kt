package com.example.movietask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movietask.model.ResultsItem

@Database(entities = [ResultsItem::class], version = 3)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesDao

}