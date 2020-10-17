package com.marcodallaba.pokeapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.data.PokemonRepository
import com.marcodallaba.pokeapp.db.PokemonDatabase
import com.marcodallaba.pokeapp.viewmodels.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [PokemonRepository] based on the [PokeService] and a
     * local cache
     */
    private fun providePokemonRepository(context: Context): PokemonRepository {
        return PokemonRepository(PokeService.create(), PokemonDatabase.getInstance(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(providePokemonRepository(context))
    }
}
