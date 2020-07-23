package com.example.personalshop.home.strategyRecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BasicCardAdapter constructor(private val abstractStrategyCard: AbstractStrategyCard) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<ICardItem> = emptyList()
    var onCardClick: ((ICardItem.Type) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}