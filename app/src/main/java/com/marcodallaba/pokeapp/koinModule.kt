package com.marcodallaba.pokeapp

import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.data.PokemonRepository
import com.marcodallaba.pokeapp.db.PokemonDatabase
import com.marcodallaba.pokeapp.viewmodels.PokemonViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val appModule = module {

    single {
        PokemonDatabase.getInstance(androidContext())
    }

    single {
        PokeService.create()
    }

    single {
        PokemonRepository(
            get(),
            get()
        )
    }

    viewModel {
        PokemonViewModel(
            get()
        )
    }
}