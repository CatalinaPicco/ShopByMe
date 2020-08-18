package com.example.personalshop

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.personalshop.ui.CategoryFragment
import com.example.personalshop.ui.EmptyFragment
import com.example.personalshop.ui.HomeResultsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_toolbar.*


class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up View model
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val toolbar1 = findViewById(R.id.app_bar) as Toolbar
        setSupportActionBar(toolbar1)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setSearchtollbar()

        viewModel?.selectedCategory?.observe(this, Observer {
            viewModel?.onCategoryChange(it)
            if (it != null) {
                tv_category.text = "Buscar en ${it.name}"
                tv_category.visibility = View.VISIBLE
            }
        })

        viewModel?.onEmpty = {
            showFragment(EmptyFragment())
        }

        viewModel?.onCleaned = {
            showFragment(CategoryFragment())
        }

        viewModel?.query?.observe(this, Observer {
            viewModel?.onChangeQuery(it)
            if (it != null && it.isNotEmpty()  ){
                tv_product.visibility = View.VISIBLE
                val spannable = SpannableString(getString(R.string.result_title, it.toUpperCase()))
                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.primary_dark)),
                    16, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                tv_product.text = spannable
            } else {
                tv_product.visibility = View.GONE
            }
        })

        viewModel?.onSearchClick = {
            showFragment(HomeResultsFragment())
        }

        tv_category.setOnClickListener {
            tv_category.text = ""
            tv_category.visibility = View.GONE
            viewModel?.emptyCategory()
        }

        showFragment(CategoryFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    fun setSearchtollbar() {
        if (searchtoolbar != null) {
            searchtoolbar.inflateMenu(R.menu.menu_search)
            val search_menu = searchtoolbar.menu

            searchtoolbar.setNavigationOnClickListener{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar, 1, true, false)
                else
                    searchtoolbar.visibility = View.GONE
            }

            val item_search = search_menu.findItem(R.id.action_filter_search)

            item_search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar, 1, true, false)
                    } else
                        searchtoolbar.visibility = View.GONE
                    return true
                }
            })

            initSearchView()


        } else
            Log.d("toolbar", "setSearchtollbar: NULL")
    }

    fun initSearchView() {
        val searchView = searchtoolbar.menu.findItem(R.id.action_filter_search).actionView as SearchView
        searchView.isSubmitButtonEnabled = false

        val closeButton = searchView.findViewById<View>(R.id.search_close_btn) as ImageView
        closeButton.setImageResource(R.drawable.ic_close_24dp)
        closeButton.setOnClickListener {
            searchView.setQuery("", false)
            searchView.clearFocus()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(R.id.searchtoolbar, 1, true, false)
            } else
                searchtoolbar.visibility = View.GONE
        }

        // set hint and the text colors
        val txtSearch = searchView.findViewById<View>(R.id.search_src_text) as EditText
        txtSearch.hint = "Buscar productos ..."
        txtSearch.setHintTextColor(ContextCompat.getColor(this, R.color.secondary_text))
        txtSearch.setTextColor(ContextCompat.getColor(this,R.color.primary_text))

        val searchTextView =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as AutoCompleteTextView
        try {
            val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = false
            mCursorDrawableRes.set(
                searchTextView, null
            ) //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (e: Exception) {
            e.printStackTrace()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                //viewModel?.emptyQuery()
                searchView.clearFocus()
                showFragment(HomeResultsFragment())
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //callSearch(newText)
                return false
            }

            fun callSearch(query: String) {
                //Do searching
                viewModel?.query?.value = query
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_status -> {
                showFragment(CategoryFragment())
                return true
            }
            R.id.action_search -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar, 1, true, true)
                else
                    searchtoolbar.visibility = View.VISIBLE
                val searchItem = searchtoolbar.menu?.findItem(R.id.action_filter_search)
                searchItem?.expandActionView()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction =  manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun circleReveal(viewID: Int, posFromRight: Int, containsOverflow: Boolean, isShow: Boolean) {
        val myView = findViewById<View>(viewID)

        var width = myView.width

        if(posFromRight>0)
            width-=(posFromRight* resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2)
        if(containsOverflow)
            width-= resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)

        val cx = width
        val cy = myView.height / 2

        val anim: Animator
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, width.toFloat())
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, width.toFloat(), 0f)

        anim.duration = 220.toLong()

        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!isShow) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            }
        })

        // make the view visible and start the animation
        if (isShow)
            myView.visibility = View.VISIBLE

        // start the animation
        anim.start()

    }

    override fun onPause() {
        super.onPause()
        viewModel?.disposable?.dispose()
    }

}
