package ru.orlovvv.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.orlovvv.newsapp.data.Converters
import ru.orlovvv.newsapp.utils.Constants.DATABASE_NAME

@Database(entities = [], exportSchema = false, version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun getCacheDao(): CacheDao
    abstract fun getSavedDao(): SavedDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): NewsDatabase {
            val database =
                Room.databaseBuilder(context, NewsDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()

            return database
        }
    }

}
