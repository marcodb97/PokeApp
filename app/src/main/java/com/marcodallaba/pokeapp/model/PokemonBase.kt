package com.marcodallaba.pokeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonBase(
    var name: String,
    var url: String
) {
    //Bad Hammer :(
    @PrimaryKey
    var id: Int = url.drop(34).dropLast(1).toInt()
}