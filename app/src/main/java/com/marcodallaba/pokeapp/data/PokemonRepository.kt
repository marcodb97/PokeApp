package com.marcodallaba.pokeapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.db.PokemonDatabase
import com.marcodallaba.pokeapp.model.PokemonDetailResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class PokemonRepository(
    private val service: PokeService,
    private val database: PokemonDatabase
) {

    fun getPokemonListStream(): Flow<PagingData<PokemonBase>> {

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


    suspend fun getPokemonDetail(name: String): PokemonDetailResult {
        return try {
            PokemonDetailResult.Success(service.getPokemonDetail(name))
        } catch (exception: IOException) {
            PokemonDetailResult.Error(exception)
        } catch (exception: HttpException) {
            PokemonDetailResult.Error(exception)
        }
    }

    sealed class PokemonDetailResult {
        data class Success(val result: PokemonDetailResponse) : PokemonDetailResult()
        data class Error(val throwable: Throwable?) : PokemonDetailResult()
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}