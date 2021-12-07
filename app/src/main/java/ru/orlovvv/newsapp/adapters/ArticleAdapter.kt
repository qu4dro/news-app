package ru.orlovvv.newsapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.orlovvv.newsapp.data.model.ArticleCallback
import ru.orlovvv.newsapp.data.model.Data
import ru.orlovvv.newsapp.databinding.ItemArticleBinding
import ru.orlovvv.newsapp.databinding.ItemArticleSmallBinding

class ArticleAdapter(
    private val listener: ArticleAdapterListener,
    private val isSmall: Boolean = false
) :
    ListAdapter<Data, RecyclerView.ViewHolder>(ArticleCallback) {

    interface ArticleAdapterListener {
        fun onArticleClick(cardView: View, article: Data)
    }

    class ArticleViewHolder(
        private val binding: ItemArticleBinding,
        private val listener: ArticleAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = listener
        }

        fun bind(article: Data) {
            binding.article = article
            binding.executePendingBindings()
        }
    }

    class ArticleSmallViewHolder(
        private val binding: ItemArticleSmallBinding,
        private val listener: ArticleAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listener = listener
        }

        fun bind(article: Data) {
            binding.article = article
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (isSmall) {
            return ArticleSmallViewHolder(
                ItemArticleSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                listener
            )
        } else {
            return ArticleViewHolder(
                ItemArticleBinding.inflate(
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