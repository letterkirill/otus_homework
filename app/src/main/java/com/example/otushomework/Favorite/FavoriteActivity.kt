package com.example.otushomework

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_RESULT = "EXTRA_RESULT"
        const val EXTRA_LIST = "EXTRA_LIST"
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.rv_favorite) }

    private var filmsList = mutableListOf<FilmItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        initFilmsListData(savedInstanceState)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteAdapter(filmsList, object : FavoriteAdapter.FilmsClickListener{
            override fun onDeleteClick(position: Int) {

                filmsList.removeAt(position)
                recyclerView.adapter?.notifyItemRemoved(position)
            }
        })

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun initFilmsListData(savedInstanceState: Bundle?){

        val data = savedInstanceState?.getParcelable<FilmsListData>(MainActivity.EXTRA_LIST)

        if (data == null) {
            intent.getParcelableExtra<FilmsListData>(EXTRA_LIST)?.let {
                filmsList = it.films.toMutableList()
            }
        } else {
            data?.let{filmsList = it.films.toMutableList()}
        }
    }

    override fun onBackPressed() {

        val intentR = Intent()
        intentR.putExtra(EXTRA_RESULT, FilmsListData(filmsList))
        setResult(Activity.RESULT_OK, intentR)

        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(EXTRA_LIST, FilmsListData(filmsList))
    }
}