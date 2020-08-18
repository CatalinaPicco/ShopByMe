package com.example.personalshop.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.personalshop.MainViewModel
import com.example.personalshop.R
import com.example.personalshop.detail.DetailActivity
import com.example.personalshop.ui.productCard.ProductViewHolder
import com.example.personalshop.utils.strategyRecycler.BaseRecyclerViewFragment
import com.example.personalshop.model.search.Results
import com.example.personalshop.utils.EndlessRecyclerViewScrollListener
import com.example.personalshop.utils.GenericAdapter
import kotlinx.android.synthetic.main.custom_recycler_card.view.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class HomeResultsFragment : BaseRecyclerViewFragment() {

    private var viewModel: MainViewModel? = null
    var genericAdapterProducts: GenericAdapter<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.result?.observe(viewLifecycleOwner, Observer {
            if (genericAdapterProducts != null){
                genericAdapterProducts?.setItems(it)
            } else {
                genericAdapterProducts?.setItems(emptyList())
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
                    viewModel?.loadMore()
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
}