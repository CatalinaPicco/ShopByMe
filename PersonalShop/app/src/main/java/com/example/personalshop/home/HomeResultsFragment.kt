package com.example.personalshop.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.postDelayed
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.personalshop.MainViewModel
import com.example.personalshop.R
import com.example.personalshop.detail.DetailActivity
import com.example.personalshop.home.productCard.ProductCardItem
import com.example.personalshop.home.productCard.ProductViewHolder
import com.example.personalshop.home.strategyRecycler.BaseRecyclerViewFragment
import com.example.personalshop.home.strategyRecycler.BasicCardAdapter
import com.example.personalshop.model.search.Results
import com.example.personalshop.model.search.SearchResponse
import com.example.personalshop.services.SearchService
import com.example.personalshop.utils.EndlessRecyclerViewScrollListener
import com.example.personalshop.utils.GenericAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.custom_recycler_card.view.*
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*


class HomeResultsFragment : BaseRecyclerViewFragment() {

    var productList = ArrayList<ProductCardItem>()
    private var viewModel: MainViewModel? = null
    var genericAdapterProducts: GenericAdapter<Any>? = null
    var offset = 0
    val itemsPerPage = 10

    private var disposable: Disposable? = null
    private val searchService by lazy {
        SearchService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.query?.observe(viewLifecycleOwner, Observer {
            productList.clear()
            beginSearch(it, viewModel?.selectedCategory?.value?.id)
        })

        viewModel?.selectedCategory?.observe(viewLifecycleOwner, Observer {
            productList.clear()
            beginSearch(null, it?.id)
        })

        viewModel?.result?.observe(viewLifecycleOwner, Observer {
            if (genericAdapterProducts != null){
                genericAdapterProducts?.setItems(it)
            }
        })

        productRecycler.mRecyclerView.apply {
            val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            this.layoutManager = layoutManager
        }

        setRecycler()

        productRecycler.recyclerview.apply {
            addOnScrollListener(object: EndlessRecyclerViewScrollListener(layoutManager as StaggeredGridLayoutManager){
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    if (!viewModel?.isAllLoaded!! && !viewModel?.isLoading?.value!!){
                        beginSearch(viewModel?.query?.value, null)
                        offset += itemsPerPage
                        viewModel?.isLoading?.value = true
                    }

                }
            })
        }

    }

    private fun setRecycler() {
        genericAdapterProducts = object : GenericAdapter<Any>(emptyList()){
            override fun getLayoutId(position: Int, obj: Any): Int {
                return when (obj){
                    is Results -> R.layout.product_card_layout
                    else -> R.layout.product_card_layout
                }
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return when (viewType){
                    R.layout.product_card_layout -> {
                        val productViewHolder = ProductViewHolder(view)
                        productViewHolder.onItemClick = this@HomeResultsFragment::onItemClick
                        productViewHolder
                    }
                    else -> ProductViewHolder(view)
                }
            }
        }

        productRecycler.setGenericAdapter(genericAdapterProducts!!)

    }

    private fun onItemClick(data: Results) {
        println(data.title)
        val intent = Intent(this.activity, DetailActivity::class.java)
        intent.putExtra("productId", data.id)
        intent.putExtra("productRating", data.seller.seller_reputation.transactions.ratings.positive.toString())
        startActivity(intent)
    }

    private fun beginSearch(searchString: String?, category: String?) {
        disposable = searchService.searchProducts(searchString,offset, itemsPerPage, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        viewModel?.doStuff(result.results)
                    }
                },
                { error -> println("error: " + error.message) }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}