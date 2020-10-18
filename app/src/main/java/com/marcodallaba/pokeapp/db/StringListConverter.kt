package com.marcodallaba.pokeapp.db

import androidx.room.TypeConverter

/**
 * I know I should use tables with relationship
 */
class StringListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}