package com.juniorlanga.swapistarwars.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.juniorlanga.swapistarwars.data.CharactersPagingSource
import com.juniorlanga.swapistarwars.models.Character
import com.juniorlanga.swapistarwars.network.ApiService
import com.juniorlanga.swapistarwars.network.SafeApiCall
import com.juniorlanga.swapistarwars.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) : SafeApiCall() {

    fun getCharacters(searchString: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(apiService, searchString)
            }
        ).flow
    }

    suspend fun getFilm(url: String) = safeApiCall {
        apiService.getFilm(url)
    }

}