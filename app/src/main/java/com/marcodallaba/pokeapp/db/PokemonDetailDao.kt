package com.marcodallaba.pokeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcodallaba.pokeapp.model.PokemonDetailResponse

@Dao
interface PokemonDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonDetailResponse)

    @Query("SELECT * FROM pokemonDetail WHERE name=:name")
    fun getPokemonByName(name: String): PokemonDetailResponse?

    @Query("DELETE FROM pokemonDetail")
    suspend fun clearPokemonDetail()

}