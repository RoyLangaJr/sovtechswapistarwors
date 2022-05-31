package com.juniorlanga.swapistarwars.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.juniorlanga.swapistarwars.data.CharactersRepository
import com.juniorlanga.swapistarwars.models.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charactersRepository: CharactersRepository) :
    ViewModel() {

    fun getCharacters(searchString: String): Flow<PagingData<Character>> {
        return charactersRepository.getCharacters(searchString).cachedIn(viewModelScope)
    }
}