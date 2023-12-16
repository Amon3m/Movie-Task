package com.example.movietask.main.view

import com.example.movietask.model.MoviesResponse
import com.example.movietask.model.ResultsItem

interface OnMovieClickListener {
    fun onMovieClick( resultsItem: ResultsItem?)

}
