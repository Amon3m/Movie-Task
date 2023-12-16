package com.example.movietask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesResponse(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)
@Entity(tableName = "movies")

data class ResultsItem(
	@PrimaryKey
	@ColumnInfo
	@field:SerializedName("id")
	var id: Int? = null,
	@ColumnInfo

	@field:SerializedName("title")
	var title: String? = null,
	@ColumnInfo

	@field:SerializedName("poster_path")
	var posterPath: String? = null,
	@ColumnInfo

	@field:SerializedName("release_date")
	var releaseDate: String? = null,
	@ColumnInfo

	@field:SerializedName("vote_average")
	var voteAverage: Double? = null

)
