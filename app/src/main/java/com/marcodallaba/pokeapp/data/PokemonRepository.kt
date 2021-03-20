package com.marcodallaba.pokeapp.data

import androidx.paging.*
import com.marcodallaba.pokeapp.api.PokeService
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.db.PokemonDatabase
import com.marcodallaba.pokeapp.model.PokemonDetail
import com.marcodallaba.pokeapp.model.PokemonDetailResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class PokemonRepository(
    private val service: PokeService,
    private val database: PokemonDatabase
) {

    @ExperimentalPagingApi
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
        var pokemonDetail = database.pokemonDetailDao().getPokemonByName(name)

        if (pokemonDetail != null) {
            return PokemonDetailResult.Success(pokemonDetail)
        }
        return try {
            pokemonDetail = service.getPokemonDetail(name).map()
            database.pokemonDetailDao().insert(pokemonDetail)
            PokemonDetailResult.Success(pokemonDetail)
        } catch (exception: IOException) {
            PokemonDetailResult.Error(exception)
        } catch (exception: HttpException) {
            PokemonDetailResult.Error(exception)
        }
    }

    sealed class PokemonDetailResult {
        data class Success(val result: PokemonDetail) : PokemonDetailResult()
        data class Error(val throwable: Throwable?) : PokemonDetailResult()
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}