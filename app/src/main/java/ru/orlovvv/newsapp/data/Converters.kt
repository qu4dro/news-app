package ru.orlovvv.newsapp.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromCategories(value: List<String>): String {
        return value.joinToString()
    }

    @TypeConverter
    fun toCategories(value: String): List<String> {
        return value.split(", ")
    }

}