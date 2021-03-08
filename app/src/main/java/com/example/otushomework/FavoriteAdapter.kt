package com.example.otushomework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(private val items: List<FilmItem>, private val clickListener: FilmsClickListener?): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        private val titleFilmView: TextView = itemView.findViewById(R.id.titleFilm)
        private val imageFilm: ImageView = itemView.findViewById(R.id.imageFilm)
        val imageViewButton:ImageView = itemView.findViewById(R.id.imageViewButton)

        fun bind(item: FilmItem){
            titleFilmView.setText(item.title)
            imageFilm.setImageResource(item.idImage)
            imageViewButton.setImageResource(R.drawable.ic_baseline_delete_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_film, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        
        holder.imageViewButton.setOnClickListener{clickListener?.onDeleteClick(item.id, holder.adapterPosition)}
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface FilmsClickListener{
        fun onDeleteClick(id: Int, position: Int)
    }
}