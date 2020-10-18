package com.marcodallaba.pokeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcodallaba.pokeapp.model.PokemonBase
import com.marcodallaba.pokeapp.model.PokemonDetail

@Database(
    entities = [PokemonBase::class, RemoteKeys::class, PokemonDetail::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun pokemonDetailDao() : PokemonDetailDao

    companion object {

        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PokemonDatabase::class.java, "Pokemon.db")
                .build()
    }
}