package com.marcodallaba.pokeapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.db.PokemonDatabase
import com.marcodallaba.pokeapp.db.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val service: PokeService,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokemonBase>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonBase>
    ): MediatorResult {

        val offset = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(state.config.pageSize) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null)
                    STARTING_PAGE_INDEX
                else remoteKeys.nextKey ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }
        }

        try {
            val apiResponse = service.getPokemonList(offset, state.config.pageSize)

            val pokemon = apiResponse.results
            val endOfPaginationReached = pokemon.isEmpty()
            pokemonDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.remoteKeysDao().clearRemoteKeys()
                    pokemonDatabase.pokemonDao().clearPokemon()
                }
                val prevKey = if (offset == STARTING_PAGE_INDEX) null else offset - state.config.pageSize
                val nextKey = if (endOfPaginationReached) null else offset + state.config.pageSize
                val keys = pokemon.map {
                    RemoteKeys(pokemonId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                pokemonDatabase.remoteKeysDao().insertAll(keys)
                pokemonDatabase.pokemonDao().insertAll(pokemon)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonBase>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                // Get the remote keys of the last item retrieved
                pokemonDatabase.remoteKeysDao().remoteKeysPokemonId(pokemonId = pokemon.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonBase>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { pokemonUrl ->
                pokemonDatabase.remoteKeysDao().remoteKeysPokemonId(pokemonId = pokemonUrl)
            }
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }
}