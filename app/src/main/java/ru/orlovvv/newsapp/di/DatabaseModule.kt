package ru.orlovvv.newsapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.orlovvv.newsapp.data.db.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCacheDao(db: NewsDatabase) = db.getCacheDao()

    @Provides
    @Singleton
    fun provideSavedDao(db: NewsDatabase) = db.getSavedDao()
}