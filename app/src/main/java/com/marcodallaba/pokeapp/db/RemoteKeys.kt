package com.marcodallaba.pokeapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
        @PrimaryKey val pokemonUrl: String,
        val prevKey: Int?,
        val nextKey: Int?
)
