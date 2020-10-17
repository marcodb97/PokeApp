package com.marcodallaba.pokeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonDetail")
data class PokemonDetail(
    val id: Int,
    @PrimaryKey
    val name: String,
    val type: String,
    val frontPic: String,
    val backPic: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)