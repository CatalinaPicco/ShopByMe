package com.example.personalshop.utils.strategyRecycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractStrategyCard {

    private var iViewType: IViewType? = null

    fun getLayout(): Int? {
        return iViewType?.getLayout()
    }

    abstract fun setStrategy(iCardItem: ICardItem)

    protected fun setIViewType(iViewType: IViewType){
        this.iViewType = iViewType
    }

    fun getViewHolder(viewHolder: View): RecyclerView.ViewHolder {
        return iViewType!!.getViewHolder(viewHolder)
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, iCardItem: ICardItem){
        iViewType!!.onBindViewHolder(holder, iCardItem)
    }

}