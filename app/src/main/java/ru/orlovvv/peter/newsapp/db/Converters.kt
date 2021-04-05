package ru.orlovvv.peter.newsapp.db

import androidx.room.TypeConverter
import ru.orlovvv.peter.newsapp.models.news.Source
import ru.orlovvv.peter.newsapp.models.news_sources.SourceResponse

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}