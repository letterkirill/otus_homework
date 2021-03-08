package com.example.otushomework

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmsAdapter(private val items: List<FilmItem>, private val clickListener: FilmsClickListener?): RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {

    class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleFilmView: TextView = itemView.findViewById(R.id.titleFilm)
        private val imageFilm: ImageView = itemView.findViewById(R.id.imageFilm)
        val imageViewFavorite: ImageView = itemView.findViewById(R.id.imageViewButton)

        fun bind(item: FilmItem){

            titleFilmView.setText(item.title)
            if (item.clicked)
                titleFilmView.setTextColor(Color.BLUE)
            else
                titleFilmView.setTextColor(Color.BLACK)

            if (item.favorite)
                imageViewFavorite.setImageResource(R.drawable.ic_baseline_star_24_yellow)
            else
                imageViewFavorite.setImageResource(R.drawable.ic_baseline_star_border_24_grey)

            imageFilm.setImageResource(item.idImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsAdapter.FilmsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_film, parent, false)

        return FilmsAdapter.FilmsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)

        holder.titleFilmView.setOnClickListener{
            clickListener?.onFilmClick(item, holder.adapterPosition)
        }

        holder.imageViewFavorite.setOnClickListener{
            clickListener?.onFavoriteClick(item, holder.adapterPosition)
        }
    }

    interface FilmsClickListener{
        fun onFilmClick(filmItem:FilmItem, position: Int)
        fun onFavoriteClick(filmItem:FilmItem, position: Int)
    }
}