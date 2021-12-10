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
import ru.orlovvv.newsapp.databinding.ItemArticleV3Binding

class ArticleAdapter(
    private val listener: ArticleAdapterListener,
    private val isSaved: Boolean = false
) :
    ListAdapter<Article, RecyclerView.ViewHolder>(ArticleCallback) {

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
        const val VIEW_TYPE_THREE = 3
    }

    interface ArticleAdapterListener {
        fun onArticleClick(cardView: View, article: Article)
    }

    class ArticleViewHolderV1(
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

    class ArticleViewHolderV2(
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

    class ArticleViewHolderV3(
        private val binding: ItemArticleV3Binding,
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
        when {
            isSaved -> {
                return ArticleViewHolderV3(
                    ItemArticleV3Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    listener
                )
            }
            viewType == 1 -> {
                return ArticleViewHolderV1(
                    ItemArticleV1Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), listener
                )
            }
            else -> {
                return ArticleViewHolderV2(
                    ItemArticleV2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), listener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = getItem(position)
        when (holder) {
            is ArticleViewHolderV2 -> holder.bind(article)
            is ArticleViewHolderV1 -> holder.bind(article)
            is ArticleViewHolderV3 -> holder.bind(article)
        }
    }
}