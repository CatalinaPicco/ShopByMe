package com.example.personalshop.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.personalshop.R
import com.example.personalshop.home.productCard.ProductCardItem
import com.example.personalshop.model.search.Results
import com.example.personalshop.services.SearchService
import com.squareup.picasso.Picasso
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*

class DetailActivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Setting up View model
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel?.product = intent.getSerializableExtra("product") as Results


        setViews()

    }

    private fun setViews() {
        val picasso = Picasso.get()
        picasso.load(viewModel?.product?.thumbnail)
            .into(iv_detail)
        tv_detail_title.text = viewModel?.product?.title
        rb_detail.rating = 4.5F
    }

}
