package ru.orlovvv.newsapp.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    var author: String? = "Author",
    var content: String? = "Content not found",
    var description: String? = "Description not found",
    var publishedAt: String?,
    var source: Source?,
    var title: String? = "Title not found",
    @PrimaryKey(autoGenerate = false)
    var url: String,
    var urlToImage: String? = "Url not found"
)

object ArticleCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
        oldItem: Article,
        newItem: Article
    ) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ) =
        oldItem == newItem

}