package com.example.personalshop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.MainViewModel
import com.example.personalshop.R
import com.example.personalshop.ui.categoryCard.CategoryViewHolder
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.search.Results
import com.example.personalshop.utils.GenericAdapter
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoryFragment: Fragment() {

    private var viewModel: MainViewModel? = null
    var genericAdapterProducts: GenericAdapter<Any>? = null

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
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        if(!viewModel!!.alreadyExecuted) {
            viewModel?.getCategories()
            viewModel!!.alreadyExecuted = true
        }

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

    private fun onItemClick(data: Category) {
        viewModel?.selectedCategory?.value = data
        viewModel?.onSearchClick?.invoke()
    }

}