package ru.orlovvv.peter.newsapp.adapters

import android.transition.AutoTransition
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.item_article.view.*
import ru.orlovvv.peter.newsapp.databinding.ItemArticleBinding
import ru.orlovvv.peter.newsapp.models.news.Article
import ru.orlovvv.peter.newsapp.ui.NewsViewModel

class NewsAdapter(private var viewModel: NewsViewModel) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsCallBack()) {

    var previousExpandedPosition = -1

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
//        var expandedPosition = -1
        val isExpanded = position == -1

        holder.itemView.cl_expand_information.visibility =
            if (!isExpanded) View.GONE else View.VISIBLE
        holder.itemView.cl_expand_information_preview.visibility =
            if (!isExpanded) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            previousExpandedPosition = if (isExpanded) -1 else position
            val transition = ChangeBounds()
            if (it.cl_expand_information.isGone && !isExpanded) {
                transition.duration = 300L
                TransitionManager.beginDelayedTransition(
                    holder.itemView.cv_article,
                    transition
                )
                it.cl_expand_information.visibility = View.VISIBLE
                it.cl_expand_information_preview.visibility = View.GONE
            } else {
                transition.duration = 0L
                TransitionManager.beginDelayedTransition(
                    holder.itemView.cv_article,
                    transition
                )
                it.cl_expand_information.visibility = View.GONE
                it.cl_expand_information_preview.visibility = View.VISIBLE
//
            }
        }

        holder.itemView.btn_source.setOnClickListener {
            onSourceClickListener?.let {
                it(article)
            }
        }

        holder.itemView.btn_save.setOnClickListener {
            viewModel.saveToReadLater(article)
        }

        holder.itemView.btn_share.setOnClickListener {
            onShareClickListener?.let {
                it(article.url)
            }
        }

        holder.bind(article)
    }

    private var onSourceClickListener: ((Article) -> Unit)? = null

    private var onShareClickListener: ((String?) -> Unit)? = null

    fun setOnSourceClickListener(listener: (Article) -> Unit) {
        onSourceClickListener = listener
    }

    fun setOnShareClickListener(listener: (String?) -> Unit) {
        onShareClickListener = listener
    }

    fun getItemWithPosition(position: Int): Article {
        return getItem(position)
    }
}