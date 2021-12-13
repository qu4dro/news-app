package ru.orlovvv.newsapp.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.orlovvv.newsapp.data.model.Article
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("articleList")
fun bindArticleList(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as ArticleAdapter
    adapter.submitList(data)
}

@BindingAdapter("category")
fun bindCategory(textView: MaterialTextView, categories: List<String>?) {
    textView.text = categories?.get(0)?.replaceFirstChar { c: Char -> c.uppercaseChar() }
}


@BindingAdapter("articleText")
fun bindArticleText(textView: MaterialTextView, text: String?) {
    if (text != null) {
        if (text.endsWith(".")) {
            textView.text = text
        } else {
            val newText = "$text."
            textView.text = newText
        }
    }
}

@BindingAdapter("imageUrl", "cardView")
fun bindImage(imgView: ImageView, urlToImage: String?, cardView: MaterialCardView?) {
    Glide.with(imgView.context)
        .clear(imgView)
    Glide.with(imgView.context)
        .asBitmap()
        .fitCenter()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .format(DecodeFormat.PREFER_RGB_565)
        .load(urlToImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imgView.setImageBitmap(resource)
                imgView.visibility = View.VISIBLE
                cardView?.visibility = View.VISIBLE
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                imgView.setImageBitmap(null)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                imgView.setImageBitmap(null)
                    Timber.d("Error image $urlToImage")
                    imgView.visibility = View.GONE
                    cardView?.visibility = View.GONE
                // imgView.setImageResource(R.drawable.ic_article)
            }
        })
}

@BindingAdapter("bigImageUrl")
fun bindBigImage(imgView: ImageView, urlToImage: String?) {
    Glide.with(imgView.context)
        .asBitmap()
        .fitCenter()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .format(DecodeFormat.PREFER_RGB_565)
        .load(urlToImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imgView.setImageBitmap(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }
            override fun onLoadFailed(errorDrawable: Drawable?) {
            }
        })
}

@BindingAdapter("today")
fun bindToday(textView: MaterialTextView, parameter: Int?) {
    textView.text = getToday()
}

@BindingAdapter("author", "source", "date")
fun bindBadges(textView: MaterialTextView, author: String?, source: String?, date: String?) {
    val formattedAuthor = author?.replaceFirstChar { c: Char -> c.uppercaseChar() } ?: "Author"
    val formattedSource = source?.replaceFirstChar { c: Char -> c.uppercaseChar() } ?: "Global"
    val formattedDate = date?.let { formatDate(it) }
    val delimiter = " \u00b7 "
    val text = formattedSource + delimiter + formattedAuthor + delimiter + formattedDate
    textView.text = text
}


fun formatDate(date: String): String {
    val outputFormat: DateFormat = SimpleDateFormat("EEEE, d MMM HH:mm", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    return outputFormat.format(inputFormat.parse(date))
}

fun getToday(): String =
    SimpleDateFormat("EEEE, d MMM", Locale.ENGLISH).format(Calendar.getInstance().time)