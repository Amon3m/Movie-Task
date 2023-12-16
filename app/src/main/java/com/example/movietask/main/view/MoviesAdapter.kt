package com.example.movietask.main.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movietask.R
import com.example.movietask.databinding.MoviesItemBinding
import com.example.movietask.model.ResultsItem


class MoviesAdapter(val context: Context, private val listener: OnMovieClickListener) :
    ListAdapter<ResultsItem?, MoviesViewHolder>(MoviesDiffUtil()) {
    lateinit var binding: MoviesItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = MoviesItemBinding.inflate(inflater, parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val currentObject = getItem(position)
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500/${currentObject?.posterPath}")
            .error(R.drawable.img_error)
            .placeholder(R.drawable.loading_img)
            .into(  holder.binding.imageView)


        holder.binding.titleTxt.text=currentObject?.title  ?:""
        holder.binding.dateTxt.text=currentObject?.releaseDate?.substring(0, 4) ?:""
        holder.binding.ratingBar.rating=currentObject?.voteAverage.toString().toFloat()/2

        holder.binding.imageView.setOnClickListener {
            listener.onMovieClick(currentObject)
        }
    }

}

class MoviesViewHolder(var binding: MoviesItemBinding) : RecyclerView.ViewHolder(binding.root)


class MoviesDiffUtil : DiffUtil.ItemCallback<ResultsItem?>() {
    override fun areItemsTheSame(
        oldItem: ResultsItem,
        newItem: ResultsItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ResultsItem,
        newItem: ResultsItem
    ): Boolean {
        return oldItem == newItem
    }

}