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
import com.google.android.material.textview.MaterialTextView
import ru.orlovvv.newsapp.data.model.Article
import java.lang.Exception
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
    if (text.isNullOrEmpty()) {
        textView.visibility = View.GONE
    } else {
        textView.visibility = View.VISIBLE
        textView.text = text
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, urlToImage: String?) {
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
//                imgView.setImageResource(R.drawable.ic_broken_image)
            }
        })
}

@BindingAdapter("today")
fun bindToday(textView: MaterialTextView, parameter: Int?) {
    textView.text = getToday()
}

@BindingAdapter("categories","source")
fun bindBadges(textView: MaterialTextView, categories: List<String>?, source: String?) {
    val formattedCategories = try {
        categories?.get(0)?.replaceFirstChar { c: Char -> c.uppercaseChar() }
    } catch (e: Exception) {
        "News"
    }
    val formattedSource = source?.replaceFirstChar { c: Char -> c.uppercaseChar() }
    val delimiter = " \u00b7 "
    val text = formattedCategories + delimiter + formattedSource
    textView.text = text
}

fun getToday(): String =
    SimpleDateFormat("EEEE, d MMM", Locale.ENGLISH).format(Calendar.getInstance().time)