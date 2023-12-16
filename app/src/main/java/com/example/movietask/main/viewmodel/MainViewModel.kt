package com.example.movietask.main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietask.model.RepoInterface
import com.example.movietask.network.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val repo: RepoInterface) :ViewModel(){
    private val _movies = MutableStateFlow<ApiState>(ApiState.Loading)
    val movies: StateFlow<ApiState>
        get() = _movies

init {
    getmovies()
}
    fun getmovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.emit(ApiState.Loading)

                repo.getMoviesLocal().catch { e ->
                    _movies.emit(ApiState.Failure(e.message ?: ""))
                }.collect {
                    _movies.emit(ApiState.Success(it))
                }

        }
    }

}