package com.marcodallaba.pokeapp.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val forms: List<PokemonForms>,
    val types: List<PokemonType>
)