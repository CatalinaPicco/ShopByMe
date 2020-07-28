package com.example.personalshop.home.productCard

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.model.detail.Pictures
import com.example.personalshop.model.search.Results
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card_layout.view.*
import kotlinx.android.synthetic.main.producto_image_layout.view.*

class ProductImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Pictures> {

    val image: ImageView = itemView.iv_placeholder
    val container: LinearLayout = itemView.ll_image_placeholder

    var onItemClick: ((Pictures)-> Unit)? = null

    override fun bind(data: Pictures, position: Int) {
        val picasso = Picasso.get()
        picasso.load(data.url)
            .into(image)

        container.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

}