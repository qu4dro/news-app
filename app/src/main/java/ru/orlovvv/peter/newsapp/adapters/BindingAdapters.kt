package ru.orlovvv.peter.newsapp.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.adapters.NewsAdapter
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.models.news_sources.NewsSourceInfo
import ru.orlovvv.peter.newsapp.ui.NewsViewModel
import kotlin.math.log

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as NewsAdapter
    adapter.submitList(data?.toList())

}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, urlToImage: String?) {
    Glide.with(imgView.context)
        .asBitmap()
        .fitCenter()
        .dontAnimate()
        .format(DecodeFormat.PREFER_RGB_565)
        .load(urlToImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imgView.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                imgView.setImageResource(R.drawable.ic_broken_image)
            }
        })


}


@BindingAdapter("sourcesItems")
fun setChipSourceItems(view: ChipGroup, viewModel: NewsViewModel) {
    val sources = viewModel.sourceList
    if (sources == null
        || view.childCount > 0
    ) return

    val context: Context = view.context
    for (source in sources.value!!) {
        val chip = Chip(context)
        val drawable = ChipDrawable.createFromAttributes(
            context,
            null,
            0,
            R.style.Widget_MaterialComponents_Chip_Choice
        )
        chip.text = source.name
        chip.setTextAppearance(R.style.ChipStyle_Text)
        chip.setChipDrawable(drawable)
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.checkedSources.add(buttonView.text.toString())
            } else {
                viewModel.checkedSources.remove(buttonView.text.toString())
            }
            Log.d("123", "setChipSourceItems: ${viewModel.checkedSources}")
        }
        view.addView(chip)
    }
}

//@BindingAdapter("imageUrl")
//fun setImageUrl(imageView: ImageView, url: String) {
//    Glide.with(imageView.context).load(url).into(imageView)
//}