package com.example.personalshop.home.productCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.model.search.Results
import kotlinx.android.synthetic.main.product_card_layout.view.*

class ProductCardViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    var title: TextView = itemView.tv_product_title
    var price: TextView = itemView.tv_product_price
    var image: ImageView = itemView.iv_product
    var card: CardView = itemView.cv_product
}