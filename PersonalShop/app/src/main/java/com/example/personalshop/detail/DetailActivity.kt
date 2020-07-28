package com.example.personalshop.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import com.example.personalshop.home.productCard.ProductImageViewHolder
import com.example.personalshop.model.description.DescriptionResponse
import com.example.personalshop.model.detail.Pictures
import com.example.personalshop.model.detail.ProductDetailResponse
import com.example.personalshop.model.search.Results
import com.example.personalshop.services.SearchService
import com.example.personalshop.utils.DotsIndicatorDecoration
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.custom_toolbar.*


class DetailActivity : AppCompatActivity() {

    private var viewModel: DetailViewModel? = null
    var productList = ArrayList<Pictures>()
    var genericAdapterProducts: GenericAdapter<Any>? = null
    private var disposable: Disposable? = null
    private val searchService by lazy {
        SearchService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Setting up View model
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel?.productId = intent.getStringExtra("productId")?:""

        viewModel?.productDetail?.observe(this, Observer {
            if (it != null){
                setViews(it)
                if (genericAdapterProducts != null){
                    genericAdapterProducts?.setItems(it.pictures)
                }
            }
        })

        viewModel?.productDescription?.observe(this, Observer {
            if (it != null){
                setDescription(it)
            }
        })

        imageRecycler.mRecyclerView.apply {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            this.layoutManager = layoutManager
        }

        val helper = LinearSnapHelper()
        helper.attachToRecyclerView(imageRecycler.mRecyclerView)
        imageRecycler.mRecyclerView.addItemDecoration(DotsIndicatorDecoration(5,5, 5, resources.getColor(R.color.primary), resources.getColor(R.color.primary_dark)))

        getDetail()
        getDescription()
        setRecycler()

    }

    private fun setRecycler() {
        genericAdapterProducts = object : GenericAdapter<Any>(emptyList()){
            override fun getLayoutId(position: Int, obj: Any): Int {
                return when (obj){
                    is Results -> R.layout.producto_image_layout
                    else -> R.layout.producto_image_layout
                }
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return when (viewType){
                    R.layout.producto_image_layout -> {
                        val productViewHolder = ProductImageViewHolder(view)
                        productViewHolder.onItemClick = null
                        productViewHolder
                    }
                    else -> ProductImageViewHolder(view)
                }
            }
        }

        imageRecycler.setGenericAdapter(genericAdapterProducts!!)

    }

    private fun setViews(productDetailResponse: ProductDetailResponse) {
        tv_detail_title.text = productDetailResponse.title
        rb_detail.rating = 4.5F
    }

    fun setDescription(descriptionResponse: DescriptionResponse){
        tv_product_description_content.text = "${descriptionResponse.plain_text}"
    }

    private fun getDetail() {
        disposable = searchService.getDetail(viewModel?.productId.toString(), "all")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> run { viewModel?.setResults(result) }
                },
                { error -> println("error: " + error.message) }
            )
    }

    private fun getDescription() {
        disposable = searchService.getDescription(viewModel?.productId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> viewModel?.setDescription(result) },
                { error -> println("error: " + error.message) }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
