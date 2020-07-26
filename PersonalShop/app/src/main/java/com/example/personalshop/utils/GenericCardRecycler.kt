package com.example.personalshop.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import kotlinx.android.synthetic.main.custom_recycler_card.view.*

class GenericCardRecycler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): CardView(context, attrs, defStyleAttr) {

    var mRecyclerView: RecyclerView
    var mAdapter: GenericAdapter<Any>? = null

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_recycler_card, this)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        mRecyclerView = this.recyclerview!!
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
}