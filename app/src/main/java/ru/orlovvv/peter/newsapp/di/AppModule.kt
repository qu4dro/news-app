package ru.orlovvv.peter.newsapp.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.orlovvv.peter.newsapp.api.Api
import ru.orlovvv.peter.newsapp.db.NewsDatabase
import ru.orlovvv.peter.newsapp.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOrtDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideOrtDAO(db: NewsDatabase) = db.getNewsDao()


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)
}