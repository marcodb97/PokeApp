package com.marcodallaba.pokeapp.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeWrapper>,
    val sprites: Sprites,
    val stats: List<Stat>
)

data class PokemonTypeWrapper(
    var type: PokemonType
)

data class PokemonType (
    val name: String
)

class Sprites(
    val front_default: String,
)

data class Stat(
    val base_stat: Int,
)