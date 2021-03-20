package com.marcodallaba.pokeapp.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeWrapper>,
    val sprites: Sprites,
    val stats: List<Stat>
) {

    fun map(): PokemonDetail {
        val types = types.map {
            it.type.name
        }

        return PokemonDetail(
            id = id,
            name = name,
            types = types,
            frontPic = sprites.front_default,
            hp = stats[0].base_stat,
            attack = stats[1].base_stat,
            defense = stats[2].base_stat,
            specialAttack = stats[3].base_stat,
            specialDefense = stats[4].base_stat,
            speed = stats[5].base_stat
        )
    }
}

data class PokemonTypeWrapper(
    var type: PokemonType
)

data class PokemonType(
    val name: String
)

class Sprites(
    val front_default: String,
)

data class Stat(
    val base_stat: Int,
)