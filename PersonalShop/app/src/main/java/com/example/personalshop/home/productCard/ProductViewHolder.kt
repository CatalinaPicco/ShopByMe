package com.example.personalshop.home.productCard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.model.search.Results
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card_layout.view.*

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Results> {

    val title: TextView = itemView.tv_product_title
    val price: TextView = itemView.tv_product_price
    val image: ImageView = itemView.iv_product
    val container: CardView = itemView.cv_product

    var onItemClick: ((Results)-> Unit)? = null

    override fun bind(data: Results, position: Int) {
        title.text = data.title
        price.text = data.price
        val picasso = Picasso.get()
        picasso.load(data.thumbnail)
            .into(image)

        container.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

}