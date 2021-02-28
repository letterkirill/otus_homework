package com.example.otushomework

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleFilmView:TextView = itemView.findViewById(R.id.titleFilm)
    private val imageFilm:ImageView = itemView.findViewById(R.id.imageFilm)
    val imageViewFavorite:ImageView = itemView.findViewById(R.id.imageViewButton)

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