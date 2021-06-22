package ru.orlovvv.peter.newsapp.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.peter.newsapp.util.Constants.Companion.SPACING
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String? {
    val pattern: String = "yyyy-MM-dd'T'HH:mm:ss"
    val outputFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)

}

fun RecyclerView.setStickersSpacing() {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.set(SPACING, SPACING, SPACING, SPACING);
        }
    })
}
