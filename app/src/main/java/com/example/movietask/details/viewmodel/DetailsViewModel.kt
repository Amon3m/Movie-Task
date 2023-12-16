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
class DetailsViewModel @Inject constructor(private val repo: RepoInterface) : ViewModel() {
    private val _movie = MutableStateFlow<ApiState>(ApiState.Loading)
    val movie: StateFlow<ApiState>
        get() = _movie


    fun getMovie(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.emit(ApiState.Loading)

            repo.getMovieByID(id).catch { e ->
                _movie.emit(ApiState.Failure(e.message ?: ""))
            }.collect {
                _movie.emit(ApiState.Success(it))
            }

        }
    }

}