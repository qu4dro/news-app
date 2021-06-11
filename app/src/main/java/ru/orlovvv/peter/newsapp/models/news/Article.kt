package ru.orlovvv.peter.newsapp.models.news


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.orlovvv.peter.newsapp.util.formatDate
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat

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
    @PrimaryKey
    val url: String,
    val urlToImage: String?
) : Serializable {

    val dateFormatted: String?
        get() = publishedAt?.formatDate()

}