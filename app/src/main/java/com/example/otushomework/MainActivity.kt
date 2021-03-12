package com.example.otushomework

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE = 1
        const val REQUEST_CODE_FAVORITE = 2
        const val EXTRA_LIST = "EXTRA_LIST"
    }

    private val buttonInvite by lazy{findViewById<View>(R.id.buttonInvite)}
    private val buttonFavorite by lazy{findViewById<View>(R.id.buttonFavorite)}

    private var filmsList:List<FilmItem> = mutableListOf()

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.films_rv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFilmsListData(savedInstanceState)

        initRecycler()
        initClickListeners()
    }

    private fun initFilmsListData(savedInstanceState: Bundle?){

        val data = savedInstanceState?.getParcelable<FilmsListData>(EXTRA_LIST)

        if(data == null){
            filmsList = mutableListOf(
                FilmItem(0, resources.getString(R.string.Title1), R.drawable.film1, resources.getString(R.string.description1), false, false),
                FilmItem(1, resources.getString(R.string.Title2), R.drawable.film2, resources.getString(R.string.description2), false, false),
                FilmItem(2, resources.getString(R.string.Title3), R.drawable.film3, resources.getString(R.string.description3), false, false),
                FilmItem(3, resources.getString(R.string.Title1), R.drawable.film1, resources.getString(R.string.description1), false, false),
                FilmItem(4, resources.getString(R.string.Title2), R.drawable.film2, resources.getString(R.string.description2), false, false),
                FilmItem(5, resources.getString(R.string.Title3), R.drawable.film3, resources.getString(R.string.description3), false, false)
            )
        }
        else{
            data?.let{filmsList = it.films}
        }
    }

    private fun initRecycler(){

        var columns :Int
        val orientation = this.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            columns = 1
        } else {
            columns = 2
        }

        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, columns, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = FilmsAdapter(filmsList, object : FilmsAdapter.FilmsClickListener {
            override fun onFilmClick(filmItem: FilmItem, position: Int) {

                filmItem.clicked = true

                recyclerView.adapter?.notifyItemChanged(position)

                val intent = Intent(this@MainActivity, DescriptionActivity::class.java)
                intent.putExtra(DescriptionActivity.EXTRA_HEADER, SomeData(filmItem.description, filmItem.idImage))

                startActivityForResult(intent, REQUEST_CODE)
            }

            override fun onFavoriteClick(filmItem: FilmItem, position: Int) {

                filmItem.favorite = !filmItem.favorite

                recyclerView.adapter?.notifyItemChanged(position)
            }
        })

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun initClickListeners(){

        buttonInvite.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite))
            intent.type = "text/plain"

            startActivity(intent)
        }

        buttonFavorite.setOnClickListener {

            val intent = Intent(this, FavoriteActivity::class.java)
            val filmsListFavorite = mutableListOf<FilmItem>()

            this.filmsList.forEach {

                if(it.favorite){
                    filmsListFavorite.add(FilmItem(it.id, it.title, it.idImage, it.description, it.clicked, it.favorite))
                }
            }

            intent.putExtra(FavoriteActivity.EXTRA_LIST, FilmsListData(filmsListFavorite))
            startActivityForResult(intent, REQUEST_CODE_FAVORITE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){

                data?.getParcelableExtra<ResultData>(DescriptionActivity.EXTRA_RESULT)?.let {
                    Log.i("RESULT_CHECKED", it.checked.toString())
                    Log.i("RESULT_TEXT", it.text)
                }
            }
        }
        else if(requestCode == REQUEST_CODE_FAVORITE){
            if(resultCode == Activity.RESULT_OK){

                data?.getParcelableExtra<FilmsListData>(FavoriteActivity.EXTRA_RESULT)?.let {
                    val resultData = it

                    filmsList.forEach {
                        val item = it

                        if(resultData.films.find { it.id == item.id } == null && item.favorite){
                            item.favorite = false
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onBackPressed() {

        val listener = DialogInterface.OnClickListener { dialogInterface, i ->
            if(i == AlertDialog.BUTTON_POSITIVE) {
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            else
                dialogInterface.dismiss()
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titleQuite))
        builder.setMessage(getString(R.string.questionQuite))
        builder.setPositiveButton(getString(R.string.responseYes), listener)
        builder.setNegativeButton(getString(R.string.responseNo), listener)

        val dialog = builder.create()
        dialog.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(EXTRA_LIST, FilmsListData(filmsList))
    }
}