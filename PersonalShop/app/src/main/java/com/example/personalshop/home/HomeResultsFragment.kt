package com.example.personalshop.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personalshop.HomeResultsStrategy
import com.example.personalshop.home.productCard.ProductCardItem
import com.example.personalshop.home.strategyRecycler.BaseRecyclerViewFragment
import com.example.personalshop.home.strategyRecycler.BasicCardAdapter
import com.example.personalshop.model.SearchResponse
import com.example.personalshop.services.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeResultsFragment : BaseRecyclerViewFragment() {

    var productList = ArrayList<ProductCardItem>()

    private var disposable: Disposable? = null
    private val searchService by lazy {
        SearchService.create()
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
        beginSearch("test")

        getRecyclerView().adapter = BasicCardAdapter(HomeResultsStrategy())
        (getRecyclerView().adapter as BasicCardAdapter).setData(
            productList
        )
    }

    private fun beginSearch(searchString: String) {
        disposable = searchService.searchProducts("peugeot",0, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> createCards(result)},
                { error -> println("error: " + error.message) }
            )
    }

    private fun createCards(result: SearchResponse?) {
       result?.results?.forEach {
           productList.add(ProductCardItem(it))
       }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}