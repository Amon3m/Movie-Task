package com.example.movietask.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.movietask.R
import com.example.movietask.databinding.FragmentMainBinding
import com.example.movietask.main.viewmodel.MainViewModel
import com.example.movietask.model.MoviesResponse
import com.example.movietask.model.ResultsItem
import com.example.movietask.network.ApiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(),OnMovieClickListener {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentMainBinding
    lateinit var moviesAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesAdapter = MoviesAdapter(requireContext(), this)

        binding.recyclerView.apply {
            adapter = moviesAdapter
        }
        lifecycleScope.launch {
            viewModel.movies.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as? MoviesResponse

                        binding.progressBar.visibility = View.INVISIBLE
                        moviesAdapter.submitList(data?.results)
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    else -> {


                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    override fun onMovieClick(resultsItem: ResultsItem?) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(
            resultsItem?.id ?: 1
        )
        Navigation.findNavController(requireView()).navigate(action)
    }
}