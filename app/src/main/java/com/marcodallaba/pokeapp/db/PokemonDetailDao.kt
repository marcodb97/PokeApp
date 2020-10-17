package com.marcodallaba.pokeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcodallaba.pokeapp.model.PokemonDetail

@Dao
interface PokemonDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonDetail)

    @Query("SELECT * FROM pokemonDetail WHERE name=:name")
    suspend fun getPokemonByName(name: String): PokemonDetail?

    @Query("DELETE FROM pokemonDetail")
    suspend fun clearPokemonDetail()

}