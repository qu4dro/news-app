package ru.orlovvv.peter.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.orlovvv.peter.newsapp.models.news.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getNewsDao(): NewsDAO

}