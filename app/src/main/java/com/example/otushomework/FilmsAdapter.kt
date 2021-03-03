package com.example.otushomework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter(private val items: List<FilmItem>, private val clickListener: FilmsClickListener): RecyclerView.Adapter<FilmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_film, parent, false)

        return FilmsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)

        holder.titleFilmView.setOnClickListener{
            clickListener.onFilmClick(item, holder.adapterPosition)
        }

        holder.imageViewFavorite.setOnClickListener{
            clickListener.onFavoriteClick(item, holder.adapterPosition)
        }
    }

    interface FilmsClickListener{
        fun onFilmClick(filmItem:FilmItem, position: Int)
        fun onFavoriteClick(filmItem:FilmItem, position: Int)
    }
}