package com.example.movietask.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.movietask.R
import com.example.movietask.databinding.FragmentDetailsBinding
import com.example.movietask.databinding.FragmentMainBinding
import com.example.movietask.details.viewmodel.DetailsViewModel
import com.example.movietask.main.viewmodel.MainViewModel
import com.example.movietask.model.DetailsResponse
import com.example.movietask.model.MoviesResponse
import com.example.movietask.network.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel by viewModels<DetailsViewModel>()

    private lateinit var binding: FragmentDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = DetailsFragmentArgs.fromBundle(requireArguments()).MovieId
        viewModel.getMovie(movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.movie.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as? DetailsResponse

                        binding.progressBar2.visibility = View.INVISIBLE

                        bindViews(data)
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        binding.progressBar2.visibility = View.INVISIBLE
                    }

                    else -> {


                        binding.progressBar2.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun bindViews(data: DetailsResponse?) {
        binding.titleTxt.text=data?.title
        binding.overTxt.text=data?.overview
        binding.genresTxt.text= data?.genres?.get(0)?.name
        binding.langTxt.text= data?.spokenLanguages?.get(0)?.englishName
        binding.ratingBar2.rating =data?.voteAverage.toString().toFloat()/2
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/${data?.posterPath}")
            .error(R.drawable.img_error)
            .placeholder(R.drawable.loading_img)
            .into(  binding.imageView3)
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/${data?.backdropPath}")
            .error(R.drawable.img_error)
            .placeholder(R.drawable.loading_img)
            .into(  binding.coverImage)
    }

}