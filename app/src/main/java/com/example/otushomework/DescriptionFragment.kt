package com.example.otushomework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DescriptionFragment: Fragment() {

    var information = ""
    var imageId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val textView = view.findViewById<TextView>(R.id.textInformation)
        textView.setText(information)

        val imageView = view.findViewById<ImageView>(R.id.imageViewFilm)
        imageView.setImageResource(imageId)
    }
}