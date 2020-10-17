package com.marcodallaba.pokeapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.api.PokemonBase
import com.marcodallaba.pokeapp.db.PokemonDatabase
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val service: PokeService,
    private val database: PokemonDatabase
) {

    fun getSearchResultStream(): Flow<PagingData<PokemonBase>> {

        val pagingSourceFactory = { database.pokemonDao().pokemonOrderedById() }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = PokemonRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}