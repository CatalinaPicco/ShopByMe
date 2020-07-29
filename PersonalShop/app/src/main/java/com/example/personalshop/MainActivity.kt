package com.example.personalshop

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.personalshop.home.HomeResultsFragment
import com.example.personalshop.services.SearchService
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.categories_layout.*
import com.example.personalshop.utils.extensions
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null
    private var disposable: Disposable? = null
    private val searchService by lazy {
        SearchService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up View model
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        edit_search.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel?.query?.value = query
                return false
            }

        })

        viewModel?.selectedCategory?.observe(this, Observer {
            if (it != null) {
                tv_category.text = "Buscar en ${it.name}"
                tv_category.visibility = View.VISIBLE
            }
        })

        viewModel?.onSearchClick = {
            showFragment(HomeResultsFragment())
        }

        tv_category.setOnClickListener {
            viewModel?.selectedCategory?.value = null
            tv_category.text = ""
            tv_category.visibility = View.GONE
            edit_search.setQuery("", false);
            edit_search.clearFocus();
        }

        navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
               R.id.action_search -> {
                   showFragment(HomeResultsFragment())
               }
                R.id.action_navigation -> {
                    showFragment(CategoryFragment())
                }
                R.id.action_categories -> {
                    showFragment(CategoryFragment())
                }
            }
            true
        }

        showFragment(HomeResultsFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction =  manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
