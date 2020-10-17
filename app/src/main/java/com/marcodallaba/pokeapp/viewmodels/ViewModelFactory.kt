package com.marcodallaba.pokeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcodallaba.pokeapp.data.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: PokemonRepository) : ViewModelProvider.Factory {

    @ExperimentalCoroutinesApi
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchPokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchPokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
