package com.example.otushomework

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CLICKED = "EXTRA_CLICKED"
        const val REQUEST_CODE = 1
    }

    private val textView1 by lazy{findViewById<TextView>(R.id.textView1)}
    private val textView2 by lazy{findViewById<TextView>(R.id.textView2)}
    private val textView3 by lazy{findViewById<TextView>(R.id.textView3)}

    private val button1 by lazy{findViewById<View>(R.id.button1)}
    private val button2 by lazy{findViewById<View>(R.id.button2)}
    private val button3 by lazy{findViewById<View>(R.id.button3)}
    private val buttonInvite by lazy{findViewById<View>(R.id.buttonInvite)}

    private var clicked1 = false
    private var clicked2 = false
    private var clicked3 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.getSerializable(EXTRA_CLICKED)?.let {
            val clickedData = it as ClickedData
            clicked1 = clickedData.clicked1
            clicked2 = clickedData.clicked2
            clicked3 = clickedData.clicked3

            if (clicked1){textView1.setTextColor(Color.BLUE)}
            if (clicked2){textView2.setTextColor(Color.BLUE)}
            if (clicked3){textView3.setTextColor(Color.BLUE)}
        }

        button1.setOnClickListener(this::clickListener)
        button2.setOnClickListener(this::clickListener)
        button3.setOnClickListener(this::clickListener)

        buttonInvite.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite))
            intent.type = "text/plain"

            startActivity(intent)
        }
    }

    private fun clickListener(view: View){

        lateinit var description:String
        var idImage = 0
        lateinit var textViewCurrent:TextView

        if (view.id == R.id.button1){

            textViewCurrent = textView1
            description = resources.getString(R.string.description1)
            idImage = R.drawable.film1
            clicked1 = true
        }
        else if (view.id == R.id.button2){

            textViewCurrent = textView2
            description = resources.getString(R.string.description2)
            idImage = R.drawable.film2
            clicked2 = true
        }
        else if (view.id == R.id.button3){

            textViewCurrent = textView3
            description = resources.getString(R.string.description3)
            idImage = R.drawable.film3
            clicked3 = true
        }
        else return

        textViewCurrent.setTextColor(Color.BLUE)

        val intent = Intent(this, DescriptionActivity::class.java)
        intent.putExtra(DescriptionActivity.EXTRA_HEADER, SomeData(description, idImage))

        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(EXTRA_CLICKED, ClickedData(clicked1, clicked2, clicked3))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){

                data?.getSerializableExtra(DescriptionActivity.EXTRA_RESULT)?.let {

                    val resultData = it as ResultData
                    Log.i("RESULT_CHECKED", resultData.checked.toString())
                    Log.i("RESULT_TEXT", resultData.text)
                }
            }
        }
    }
}

data class ClickedData(val clicked1: Boolean, val clicked2: Boolean, val clicked3: Boolean):Serializable