package com.marcodallaba.pokeapp.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcodallaba.pokeapp.model.PokemonBase

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonBase>)

    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun pokemonOrderedById(): PagingSource<Int, PokemonBase>

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemon()

}