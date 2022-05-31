package com.juniorlanga.swapistarwars.viewmodels


import androidx.lifecycle.*
import com.juniorlanga.swapistarwars.data.CharactersRepository
import com.juniorlanga.swapistarwars.models.FilmResponse
import com.juniorlanga.swapistarwars.models.Character
import com.juniorlanga.swapistarwars.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val myArguments = savedStateHandle.get<Character>("characterDetails")

    private val _details = MutableLiveData<Character>()
    val details: LiveData<Character>
        get() = _details

    private val _filmDetails = MutableStateFlow<Resource<List<FilmResponse>>>(Resource.Empty())
    val filmResponseDetails: StateFlow<Resource<List<FilmResponse>>>
        get() = _filmDetails

    private val filmsList: ArrayList<FilmResponse> = ArrayList()

    init {
        _details.value = myArguments!!
        getFilmData()
    }

    private fun getFilmData() {
        myArguments!!.films.forEach { film ->

            viewModelScope.launch(Dispatchers.IO) {
                _filmDetails.value = Resource.Loading()
                when (val characterDetailsResponse = charactersRepository.getFilm(film)) {
                    is Resource.Failure -> {
                        _filmDetails.value =
                            Resource.Failure(characterDetailsResponse.message!!)
                    }
                    is Resource.Success -> {
                        if (characterDetailsResponse.data == null) {
                            _filmDetails.value = Resource.Failure("Empty Film Response List")
                        } else {
                            filmsList.add(characterDetailsResponse.data)
                            _filmDetails.value = Resource.Success(filmsList)
                        }
                    }
                }
            }
        }
    }

}