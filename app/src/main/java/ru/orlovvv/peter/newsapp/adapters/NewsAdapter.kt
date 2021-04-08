package ru.orlovvv.peter.newsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.databinding.ItemArticleBinding
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class NewsAdapter(var viewModel: NewsViewModel) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsCallBack()) {

    class NewsViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
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
                it(article)
            }
        }
        holder.itemView.tv_save.setOnClickListener {
            viewModel.saveToReadLater(article)
            holder.itemView.cv_article.isChecked = false
            hideSaveAndShareButtons(holder, holder.itemView.cv_article.isChecked)
        }
        holder.itemView.cv_article.apply {
            setOnLongClickListener {
                isChecked = !isChecked
                hideSaveAndShareButtons(holder, isChecked)
                true
            }

        }
        holder.bind(article)
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    fun getItemWithPosition(position: Int): Article {
        return getItem(position)
    }

    fun hideSaveAndShareButtons(holder: NewsViewHolder, isChecked: Boolean) {
        holder.itemView.apply {
            if (isChecked) {
                tv_save.visibility = View.VISIBLE
                tv_share.visibility = View.VISIBLE
            } else {
                tv_save.visibility = View.GONE
                tv_share.visibility = View.GONE
            }
        }

    }
}