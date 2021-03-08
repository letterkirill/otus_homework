package com.example.otushomework

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_LIST = "EXTRA_LIST"
    }

    private var filmsList: List<FilmItem> = mutableListOf()
    private val drawerLayout: DrawerLayout by lazy{findViewById(R.id.drawer_layout)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        setupDrawerContent(navigationView)

        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setDataFilms(savedInstanceState)
        if (savedInstanceState?.getSerializable(EXTRA_LIST) == null) {navigationView.menu.performIdentifierAction(R.id.nav_films, 0)}
    }

    private fun setDataFilms(savedInstanceState: Bundle?){

        val data = savedInstanceState?.getSerializable(EXTRA_LIST)

        if (data == null) {
            filmsList = mutableListOf(
                FilmItem(0, resources.getString(R.string.Title1), R.drawable.film1, resources.getString(R.string.description1), false, false),
                FilmItem(1, resources.getString(R.string.Title2), R.drawable.film2, resources.getString(R.string.description2), false, false),
                FilmItem(2, resources.getString(R.string.Title3), R.drawable.film3, resources.getString(R.string.description3), false, false),
                FilmItem(3, resources.getString(R.string.Title1), R.drawable.film1, resources.getString(R.string.description1), false, false),
                FilmItem(4, resources.getString(R.string.Title2), R.drawable.film2, resources.getString(R.string.description2), false, false),
                FilmItem(5, resources.getString(R.string.Title3), R.drawable.film3, resources.getString(R.string.description3), false, false))
        } else {
            data?.let {
                val listData = it as FilmsListData
                filmsList = listData.films
            }
        }
    }

    private fun setupDrawerContent(navigationView: NavigationView){
        navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_films -> {
                    true
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, FilmsFragment(filmsList))
                        .commit()

                }
                R.id.nav_favorites -> {

                    val filmsListFavorite = mutableListOf<FilmItem>()

                    this.filmsList.forEach {

                        if (it.favorite) {
                            filmsListFavorite.add(FilmItem(it.id, it.title, it.idImage, it.description, it.clicked, it.favorite))
                        }
                    }
                    val favoriteFragment = FavoriteFragment()
                    favoriteFragment.filmsList = filmsListFavorite

                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, favoriteFragment)
                        .commit()
                }
                R.id.nav_invite -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite))
                    intent.type = "text/plain"

                    startActivity(intent)
                }
                R.id.nav_quite -> {
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAttachFragment(fragment: Fragment) {

        if (fragment is FilmsFragment) {

            fragment.listener = object : FilmsAdapter.FilmsClickListener {
                override fun onFilmClick(filmItem: FilmItem, position: Int) {

                    filmItem.clicked = true
                    fragment.recyclerView.adapter?.notifyItemChanged(position)

                    val descriptionFragment = DescriptionFragment()
                    descriptionFragment.information = filmItem.description
                    descriptionFragment.imageId = filmItem.idImage

                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, descriptionFragment)
                            .addToBackStack(null)
                            .commit()
                }

                override fun onFavoriteClick(filmItem: FilmItem, position: Int) {

                    filmItem.favorite = !filmItem.favorite
                    fragment.recyclerView.adapter?.notifyItemChanged(position)

                    showSnackbar(getString(R.string.AddedFilm))
                }
            }
        } else if (fragment is FavoriteFragment) {

            fragment.listener = object : FavoriteAdapter.FilmsClickListener {
                override fun onDeleteClick(id: Int, position: Int) {

                    fragment.filmsList.removeAt(position)
                    fragment.recyclerView.adapter?.notifyItemRemoved(position)

                    val item = filmsList.find { it.id == id }
                    if (item != null) {
                        item.favorite = false
                    }

                    showSnackbar(getString(R.string.RemovedFilm))
                }
            }
        }
    }

    private fun showSnackbar(message: String){

        val view = findViewById<View>(R.id.drawer_layout)
        val snackbar: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {

            val listener = DialogInterface.OnClickListener { dialogInterface, i ->
                if (i == AlertDialog.BUTTON_POSITIVE) {
                    dialogInterface.dismiss()
                    finish()
                } else
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(EXTRA_LIST, FilmsListData(filmsList))
    }
}