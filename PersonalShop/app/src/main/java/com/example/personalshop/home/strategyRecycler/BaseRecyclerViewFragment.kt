package com.example.personalshop.home.strategyRecycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.personalshop.R
import kotlinx.android.synthetic.main.fragment_recyclerview.*

abstract class BaseRecyclerViewFragment: Fragment() {

    var adapter: BasicCardAdapter? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayout(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerview.layoutManager = layoutManager
    }

    fun getRecyclerView(): RecyclerView {
        return recyclerview
    }

    @LayoutRes
    fun getLayout(): Int {
        return R.layout.fragment_recyclerview
    }
}