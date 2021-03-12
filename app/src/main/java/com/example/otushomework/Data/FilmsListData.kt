package com.example.otushomework

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmsListData(val films: List<FilmItem>) : Parcelable
