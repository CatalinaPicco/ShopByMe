package com.example.personalshop.home.strategyRecycler

import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewType : IViewType {

    private var cardItem: ICardItem? = null
    private var abstractCardViewHolder: RecyclerView.ViewHolder? = null

    fun <T : RecyclerView.ViewHolder> getAbstractCardViewHolder(): T {
        return abstractCardViewHolder as T
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, iCardItem: ICardItem) {
        this.cardItem = iCardItem
        abstractCardViewHolder = holder
        onBindViewHolder()
    }

    abstract fun onBindViewHolder()

    fun <T : ICardItem> getCardItem(): T {
        return cardItem as T
    }
}