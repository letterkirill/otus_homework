package com.example.otushomework

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SomeData(val title:String, val idImage:Int): Parcelable
