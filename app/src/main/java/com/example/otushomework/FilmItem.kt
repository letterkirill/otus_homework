package com.example.otushomework

import java.io.Serializable

data class FilmItem(val id: Int, val title: String, val idImage: Int, val description: String, var clicked: Boolean, var favorite: Boolean):Serializable
