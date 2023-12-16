package com.example.movietask.details.viewmodel

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
class DetailsViewModel @Inject constructor(private val repo: RepoInterface):ViewModel() {
    private val _movies = MutableStateFlow<ApiState>(ApiState.Loading)
    val movies: StateFlow<ApiState>
        get() = _movies


    fun getmovie(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movies.emit(ApiState.Loading)

            repo.getMovieByID(id).catch { e ->
                _movies.emit(ApiState.Failure(e.message ?: ""))
            }.collect {
                _movies.emit(ApiState.Success(it))
            }

        }
    }

}