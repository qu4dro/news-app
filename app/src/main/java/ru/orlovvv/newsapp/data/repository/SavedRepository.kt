package ru.orlovvv.newsapp.data.repository

import ru.orlovvv.newsapp.data.db.SavedDao
import javax.inject.Inject

class SavedRepository @Inject constructor(
    private val savedDao: SavedDao
) {
}