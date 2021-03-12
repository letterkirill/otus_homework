package com.example.otushomework

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultData(val checked: Boolean, val text: String): Parcelable
