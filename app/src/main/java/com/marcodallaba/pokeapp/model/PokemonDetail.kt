package com.marcodallaba.pokeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonDetail")
data class PokemonDetail(
    val id: Int,
    @PrimaryKey
    val name: String,
    val types: List<String>,
    val frontPic: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
) {

    fun getTypesList(): String {
        var ret = ""
        types.forEach {
            ret+=PokemonTypeMapper.map(it)
        }
        return ret
    }
}