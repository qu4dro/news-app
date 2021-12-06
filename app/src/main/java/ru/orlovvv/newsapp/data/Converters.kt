package ru.orlovvv.newsapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun currentToString(any: Any): String = Gson().toJson(any)

}