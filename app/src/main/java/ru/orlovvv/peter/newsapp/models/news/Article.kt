package ru.orlovvv.peter.newsapp.models.news


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "articles")
data class Article(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    @PrimaryKey
    val urlToImage: String
) : Serializable {

    val dateFormatted: String
        @RequiresApi(Build.VERSION_CODES.O)
        get() = LocalDateTime.parse(
            publishedAt,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ")
        ).format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")).toString()

}