package com.marcodallaba.pokeapp.api


data class PokemonSearchResponse(
    var count: Long,
    var next: String?,
    var previous: String?,
    var results: List<PokemonBase>
)