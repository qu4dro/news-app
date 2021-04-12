package ru.orlovvv.peter.newsapp.adapters

import android.app.ActionBar
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.R
import ru.orlovvv.peter.newsapp.databinding.ItemArticleBinding
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class NewsAdapter(private var viewModel: NewsViewModel) :
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
            if (it.cl_expand_information.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.itemView.cv_article, AutoTransition())
                it.cl_expand_information.visibility = View.VISIBLE;
            } else {
                TransitionManager.beginDelayedTransition(holder.itemView.cv_article, AutoTransition())
                it.cl_expand_information.visibility = View.GONE;
            }
        }
        holder.itemView.btn_save.setOnClickListener {
            viewModel.saveToReadLater(article)
        }

        holder.itemView.btn_source.setOnClickListener {
            onSourceClickListener?.let {
                it(article)
            }
        }

        holder.itemView.btn_share.setOnClickListener {
            //TODO SHARE
        }

        holder.bind(article)
    }

    private var onSourceClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onSourceClickListener = listener
    }

    fun getItemWithPosition(position: Int): Article {
        return getItem(position)
    }
}