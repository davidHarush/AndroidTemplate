package com.david.haru.myandroidtemplate.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.david.haru.myandroidtemplate.R
import com.david.haru.myandroidtemplate.databinding.ListItemBinding
import com.david.haru.myandroidtemplate.network.MovieItem
import com.david.haru.myandroidtemplate.network.getImageUrl


class HomeAdapter(val callback: (MovieItem, ListItemBinding) -> Unit) :
    ListAdapter<MovieItem, HomeAdapter.NewsViewHolder>(
        NewsDiffCallback()
    ) {

    private lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        if (!::mInflater.isInitialized) {
            mInflater = LayoutInflater.from(parent.context)
        }

        return NewsViewHolder(mInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemBinding.bind(itemView)

        fun onBind(dataMovieItem: MovieItem) = with(binding) {
            itemView.tag = dataMovieItem
            title.text = dataMovieItem.title.trim()
            image.load(dataMovieItem.getImageUrl())

            itemView.setOnClickListener {
                val data = itemView.tag as MovieItem
                callback(data, binding)

            }
        }
    }

}

class NewsDiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldMovieItem: MovieItem, newMovieItem: MovieItem): Boolean {
        return oldMovieItem.hashCode() == newMovieItem.hashCode()
    }

    override fun areContentsTheSame(oldMovieItem: MovieItem, newMovieItem: MovieItem): Boolean {
        return oldMovieItem == newMovieItem
    }
}


