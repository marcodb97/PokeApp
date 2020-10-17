package com.marcodallaba.pokeapp.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonBase(
    var name: String,
    @PrimaryKey var url: String
)