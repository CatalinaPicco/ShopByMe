package com.example.personalshop.home.productCard

import android.provider.Settings.Global.getString
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import com.example.personalshop.model.search.Results
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_card_layout.view.*
import java.security.AccessController.getContext
import java.text.NumberFormat
import java.util.*


class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Results> {

    val title: TextView = itemView.tv_product_title
    val price: TextView = itemView.tv_product_price
    val image: ImageView = itemView.iv_product
    val container: ConstraintLayout = itemView.cv_product

    var onItemClick: ((Results)-> Unit)? = null

    override fun bind(data: Results, position: Int) {
        title.text = data.title
        price.text = price.context.resources.getString(R.string.price_format, String.format(Locale.GERMAN,"%,2f", data.price.toDouble()).dropLast(4))
        val picasso = Picasso.get()
        picasso.load(data.thumbnail)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_search_24dp)
            .error(R.drawable.ic_search_24dp)
            .into(image)

        container.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

}