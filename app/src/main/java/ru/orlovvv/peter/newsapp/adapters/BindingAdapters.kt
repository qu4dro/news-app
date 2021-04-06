package ru.orlovvv.peter.newsapp.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.models.news.Article

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
            .load(imgUri)
//            .apply(
//                RequestOptions()
//                .placeholder(R.drawable.loading_animation)
//                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

//@BindingAdapter("imageUrl")
//fun setImageUrl(imageView: ImageView, url: String) {
//    Glide.with(imageView.context).load(url).into(imageView)
//}