package com.example.personalshop.utils.strategyRecycler

import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

interface IViewType {

    @LayoutRes
    fun getLayout(): Int

    @NonNull
    fun getViewHolder(viewHolder: View): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, cardItem: ICardItem)
}