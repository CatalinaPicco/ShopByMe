package com.example.personalshop.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.personalshop.HomeResultsStrategy
import com.example.personalshop.MainViewModel
import com.example.personalshop.home.productCard.ProductCardItem
import com.example.personalshop.home.strategyRecycler.BaseRecyclerViewFragment
import com.example.personalshop.home.strategyRecycler.BasicCardAdapter
import com.example.personalshop.services.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*


class HomeResultsFragment : BaseRecyclerViewFragment() {

    var productList = ArrayList<ProductCardItem>()
    private var viewModel: MainViewModel? = null
    var isLoading = false
    var offset = 0

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
            beginSearch(it, viewModel?.selectedCategory?.value)
        })

        viewModel?.selectedCategory?.observe(viewLifecycleOwner, Observer {
            productList.clear()
            beginSearch(null, it)
        })

        viewModel?.result?.observe(viewLifecycleOwner, Observer {
            it.forEach {
                productList.add(ProductCardItem(it))
            }
            (getRecyclerView().adapter as BasicCardAdapter).setData(
                productList
            )
            (getRecyclerView().adapter as BasicCardAdapter).notifyDataSetChanged()
        })

        getRecyclerView().adapter = BasicCardAdapter(HomeResultsStrategy())

    }

    private fun beginSearch(searchString: String?, category: String?) {
        disposable = searchService.searchProducts(searchString,offset, 10, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        viewModel?.setResults(result)
                        if (result.paging.primary_results > offset){

                        }
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