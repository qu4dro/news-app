package ru.orlovvv.newsapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.orlovvv.newsapp.data.model.Source

class Converters {

    @TypeConverter
    fun sourceToString(source: Source): String = Gson().toJson(source)

    @TypeConverter
    fun stringToSource(currentForecast: String): Source =
        Gson().fromJson(currentForecast, Source::class.java)

}