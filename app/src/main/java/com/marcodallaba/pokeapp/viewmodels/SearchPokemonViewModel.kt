package com.marcodallaba.pokeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marcodallaba.pokeapp.api.PokemonBase
import com.marcodallaba.pokeapp.data.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class SearchRepositoriesViewModel(private val repository: PokemonRepository) : ViewModel() {

    fun searchPokemon(): Flow<PagingData<PokemonBase>> {
        return repository.getSearchResultStream()
            .cachedIn(viewModelScope)
    }
}