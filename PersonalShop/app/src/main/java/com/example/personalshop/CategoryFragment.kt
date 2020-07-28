package com.example.personalshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.home.categoryCard.CategoryViewHolder
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.search.Results
import com.example.personalshop.services.SearchService
import com.example.personalshop.utils.GenericAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class CategoryFragment: Fragment() {

    private var viewModel: MainViewModel? = null
    var genericAdapterProducts: GenericAdapter<Any>? = null

    private var disposable: Disposable? = null
    private val searchService by lazy {
        SearchService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java!!)
        getCategories()

        viewModel?.categories?.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (genericAdapterProducts != null){
                    genericAdapterProducts?.setItems(it)
                }
            }
        })
        setRecycler()
    }

    private fun setRecycler() {
        genericAdapterProducts = object : GenericAdapter<Any>(emptyList()){
            override fun getLayoutId(position: Int, obj: Any): Int {
                return when (obj){
                    is Results -> R.layout.category_layout
                    else -> R.layout.category_layout
                }
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return when (viewType){
                    R.layout.category_layout -> {
                        val productViewHolder = CategoryViewHolder(view)
                        productViewHolder.onItemClick = this@CategoryFragment::onItemClick
                        productViewHolder
                    }
                    else -> CategoryViewHolder(view)
                }
            }
        }

        categoryRecycler.setGenericAdapter(genericAdapterProducts!!)

    }

    private fun getCategories() {
        disposable = searchService.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> run {
                    viewModel?.categories?.value = result
                } },
                { error -> println("error: " + error.message) }
            )
    }

    private fun onItemClick(data: Category) {
        println(data.name)
        viewModel?.selectedCategory?.value = data.id
        viewModel?.onSearchClick?.invoke()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}