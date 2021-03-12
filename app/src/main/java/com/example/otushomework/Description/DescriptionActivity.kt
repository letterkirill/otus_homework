package com.example.otushomework

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class DescriptionActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_HEADER = "EXTRA_HEADER"
        const val EXTRA_RESULT = "EXTRA_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        intent.getSerializableExtra(EXTRA_HEADER)?.let {
            val someData = it as SomeData
            findViewById<TextView>(R.id.textView4).setText(someData.title)
            findViewById<ImageView>(R.id.imageView4).setImageResource(someData.idImage)
        }
    }

    override fun onBackPressed() {

        val intentR = Intent()
        intentR.putExtra(EXTRA_RESULT, ResultData(findViewById<CheckBox>(R.id.checkBox).isChecked, findViewById<TextView>(R.id.editTextComment).getText().toString()))
        setResult(Activity.RESULT_OK, intentR)

        super.onBackPressed()
    }
}