package com.example.personalshop.home.productCard

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import com.example.personalshop.home.strategyRecycler.AbstractViewType
import com.example.personalshop.home.strategyRecycler.ICardItem
import com.example.personalshop.model.search.Results
import com.squareup.picasso.Picasso

class ProductoViewType : AbstractViewType() {


    override fun onBindViewHolder() {
        val picasso = Picasso.get()
        val viewHolder = getAbstractCardViewHolder<ProductCardViewHolder>()
        viewHolder.title.text = getCardItem<ProductCardItem>().data.title
        viewHolder.price.text = getCardItem<ProductCardItem>().data.price
        picasso.load(getCardItem<ProductCardItem>().data.thumbnail)
            .into(viewHolder.image)
    }

    override fun getLayout(): Int {
        return R.layout.product_card_layout
    }

    override fun getViewHolder(viewHolder: View): RecyclerView.ViewHolder {
        return ProductCardViewHolder(viewHolder)
    }

}