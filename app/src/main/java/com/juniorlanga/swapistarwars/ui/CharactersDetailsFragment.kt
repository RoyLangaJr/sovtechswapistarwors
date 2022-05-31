package com.juniorlanga.swapistarwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.juniorlanga.swapistarwars.adapters.FilmsAdapter
import com.juniorlanga.swapistarwars.databinding.FragmentCharactersDetailsBinding
import com.juniorlanga.swapistarwars.utils.Resource
import com.juniorlanga.swapistarwars.viewmodels.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharactersDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharactersDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val filmsAdapter: FilmsAdapter by lazy {
        FilmsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.details.observe(viewLifecycleOwner, Observer { result ->
            binding.textViewFullNameValue.text = result.name
            binding.textViewSkinColorValue.text = "Skin color: " + result.skinColor
            binding.textViewHairColorValue.text = "Hair color: " + result.hairColor
            binding.textViewHeightValue.text = "Height: " + result.height
            binding.textViewGenderValue.text = "Gender: " + result.gender
            binding.textViewBirthYearValue.text = "Year: " + result.birthYear
        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.filmResponseDetails.collect { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.filmProgressBar.isVisible = false
                        filmsAdapter.submitList(event.data)
                        binding.recyclerViewFilms.adapter = filmsAdapter
                    }
                    is Resource.Failure -> {
                        binding.filmProgressBar.isVisible = false
                        binding.textViewFilmsError.isVisible = true
                        binding.textViewFilmsError.text = event.message
                    }
                    is Resource.Loading -> {
                        binding.filmProgressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }


        return view
    }
}