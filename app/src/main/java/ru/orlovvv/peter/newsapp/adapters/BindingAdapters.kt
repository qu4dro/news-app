package ru.orlovvv.peter.newsapp.util

import android.content.Context
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.models.news_sources.NewsSourceInfo

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data?.toList())

}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, urlToImage: String?) {
    urlToImage?.let {
        val imgUri =
            urlToImage.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(if (urlToImage != "") imgUri else R.drawable.ic_broken_image)
            .format(DecodeFormat.PREFER_RGB_565)
            .centerInside()
            .into(imgView)
    }
}


@BindingAdapter("items")
fun setItems(view: ChipGroup, sources: LiveData<List<NewsSourceInfo>>?) {
    if (sources == null
        || view.childCount > 0
    ) return

    val context: Context = view.context
    for (source in sources.value!!) {
        val chip = Chip(context)
        chip.text = source.name
        view.addView(chip)
    }
}
//@BindingAdapter("imageUrl")
//fun setImageUrl(imageView: ImageView, url: String) {
//    Glide.with(imageView.context).load(url).into(imageView)
//}