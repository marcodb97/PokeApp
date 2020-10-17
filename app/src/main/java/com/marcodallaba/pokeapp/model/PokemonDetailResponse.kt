package com.marcodallaba.pokeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonDetail")
data class PokemonDetailResponse(
    val id: Int,
    @PrimaryKey val name: String,
    val forms: List<PokemonForms>,
    val types: List<PokemonTypeWrapper>,
    val sprites: Sprites,
    val stats: List<Stat>
) {

}