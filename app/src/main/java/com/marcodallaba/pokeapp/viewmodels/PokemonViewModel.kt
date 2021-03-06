package com.marcodallaba.pokeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.data.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    @ExperimentalPagingApi
    fun loadPokemon(): Flow<PagingData<PokemonBase>> {
        return repository.getPokemonListStream()
            .cachedIn(viewModelScope)
    }

    suspend fun getPokemonDetail(name: String): PokemonRepository.PokemonDetailResult {
        return repository.getPokemonDetail(name)
    }
}