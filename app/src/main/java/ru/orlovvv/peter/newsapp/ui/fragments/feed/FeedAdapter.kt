package ru.orlovvv.peter.newsapp.ui.fragments.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.peter.newsapp.databinding.ItemArticleBinding
import ru.orlovvv.peter.newsapp.models.news.Article


class FeedAdapter :
    ListAdapter<Article, FeedAdapter.NewsViewHolder>(NewsCallBack()) {

    class NewsViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
            binding.apply {
                ivArticleImage.transitionName = "image_trans_${article.url}"
                tvArticleTitle.transitionName = "title_trans_${article.url}"
                tvArticleSource.transitionName = "source_trans_${article.url}"
                tvArticleDate.transitionName = "date_trans_${article.url}"
                executePendingBindings()
            }

        }

        fun provideExtras(): FragmentNavigator.Extras {
            binding.apply {
                return FragmentNavigatorExtras(
                    ivArticleImage to ivArticleImage.transitionName,
                    tvArticleTitle to tvArticleTitle.transitionName,
                    tvArticleSource to tvArticleSource.transitionName,
                    tvArticleDate to tvArticleDate.transitionName
                )
            }

        }

    }

    class NewsCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article, holder.provideExtras())
            }
        }

        holder.bind(article)
    }

    private var onItemClickListener: ((Article, FragmentNavigator.Extras) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article, FragmentNavigator.Extras) -> Unit) {
        onItemClickListener = listener
    }


}