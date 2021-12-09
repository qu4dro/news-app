package ru.orlovvv.newsapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.newsapp.data.model.Article
import ru.orlovvv.newsapp.data.model.ArticleCallback
import ru.orlovvv.newsapp.databinding.ItemArticleV1Binding
import ru.orlovvv.newsapp.databinding.ItemArticleV2Binding

class ArticleAdapter(
    private val listener: ArticleAdapterListener,
    private val isSmall: Boolean = false
) :
    ListAdapter<Article, RecyclerView.ViewHolder>(ArticleCallback) {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    interface ArticleAdapterListener {
        fun onArticleClick(cardView: View, article: Article)
    }

    class ArticleViewHolder(
        private val binding: ItemArticleV1Binding,
        private val listener: ArticleAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = listener
        }

        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
        }
    }

    class ArticleSmallViewHolder(
        private val binding: ItemArticleV2Binding,
        private val listener: ArticleAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = listener
        }

        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) VIEW_TYPE_ONE
        else {
            VIEW_TYPE_TWO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isSmall || viewType == VIEW_TYPE_TWO) {
            return ArticleSmallViewHolder(
                ItemArticleV2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
        } else {
            return ArticleViewHolder(
                ItemArticleV1Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = getItem(position)
        when (holder) {
            is ArticleSmallViewHolder -> holder.bind(article)
            is ArticleViewHolder -> holder.bind(article)
        }
    }
}