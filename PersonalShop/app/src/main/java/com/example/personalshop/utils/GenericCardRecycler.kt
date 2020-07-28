package com.example.personalshop.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.core.view.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import kotlinx.android.synthetic.main.custom_recycler_card.view.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class GenericCardRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): CardView(context, attrs, defStyleAttr) {

    var mRecyclerView: RecyclerView
    var mProgress: ProgressBar
    var mAdapter: GenericAdapter<Any>? = null

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_recycler_card, this)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        mRecyclerView = this.recyclerview!!
        mProgress = this.loading_spinner!!
        mRecyclerView.layoutManager = layoutManager
    }

    fun setGenericAdapter(genericAdapter: GenericAdapter<Any>){
        mAdapter = genericAdapter
        mRecyclerView.adapter = mAdapter
    }

    fun setData(data: List<Any>) {
        if (!data.isNullOrEmpty()){
            mAdapter?.setItems(data)
        }
    }

    fun showLoading(){
        println("showloading")
        mProgress.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }

    fun showResults(){
        println("showresult")
        mProgress.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }
}