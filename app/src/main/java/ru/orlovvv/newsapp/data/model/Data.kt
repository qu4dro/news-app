package ru.orlovvv.newsapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_data")
data class Data(
    var categories: List<String>,
    var description: String,
    var image_url: String,
    var keywords: String,
    var language: String,
    var locale: String,
    var published_at: String,
    var relevance_score: Double?,
    var snippet: String,
    var source: String,
    var title: String,
    var url: String,
    @PrimaryKey(autoGenerate = false)
    var uuid: String
)