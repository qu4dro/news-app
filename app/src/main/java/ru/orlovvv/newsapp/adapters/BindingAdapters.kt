package ru.orlovvv.newsapp.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.orlovvv.newsapp.data.model.Data

@BindingAdapter("articleList")
fun bindArticleList(recyclerView: RecyclerView, data: List<Data>?) {
    val adapter = recyclerView.adapter as ArticleAdapter
    adapter.submitList(data)
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