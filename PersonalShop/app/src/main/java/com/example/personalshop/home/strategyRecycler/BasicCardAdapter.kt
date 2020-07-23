package com.example.personalshop.home.strategyRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class BasicCardAdapter constructor(private val abstractStrategyCard: AbstractStrategyCard) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<ICardItem> = emptyList()
    var onCardClick: ((ICardItem.Type) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            abstractStrategyCard.getLayout()!!, parent, false
        )
        return abstractStrategyCard.getViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        abstractStrategyCard.setStrategy(data[position])
        return data[position].type.id
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        abstractStrategyCard.onBindViewHolder(holder, data[position])
        holder.itemView.setOnClickListener { this.onClick(position) }
    }

    private fun onClick(position: Int) {
        onCardClick?.invoke(data[position].type)
    }

    fun setData(@NonNull cardItems: List<ICardItem>) {
        this.data = Collections.unmodifiableList(cardItems)
    }

}