package com.example.movietask.di

import android.app.Application
import androidx.room.Room
import com.example.movietask.database.ConcreteLocalSource
import com.example.movietask.database.LocalSource
import com.example.movietask.database.MovieDatabase
import com.example.movietask.database.MoviesDao
import com.example.movietask.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
private const val BASE_URL= "https://api.themoviedb.org/"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()).build())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(application, MovieDatabase::class.java, "movie_database").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MoviesDao {
        return movieDatabase.movieDao()
    }

    @Provides
    @Singleton
     fun bindLocalSource(moviesDao: MoviesDao): LocalSource{
         return ConcreteLocalSource(moviesDao)
     }
}