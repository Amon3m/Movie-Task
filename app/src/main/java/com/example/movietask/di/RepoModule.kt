package com.example.movietask.di

import com.example.movietask.database.LocalSource
import com.example.movietask.database.MoviesDao
import com.example.movietask.model.RepoInterface
import com.example.movietask.model.Repository
import com.example.movietask.network.ApiClient
import com.example.movietask.network.RemoteSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class  RepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteSource(apiClient: ApiClient): RemoteSource

    @Binds
    @ViewModelScoped
    abstract fun bindRepo(repository: Repository): RepoInterface

}
