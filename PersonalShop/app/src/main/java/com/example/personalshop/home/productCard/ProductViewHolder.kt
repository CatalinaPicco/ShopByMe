package com.example.personalshop.home.productCard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import com.example.personalshop.model.search.Results
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.product_card_layout.view.*
import java.lang.Exception
import java.util.*


class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Results> {

    val title: TextView = itemView.tv_product_title
    val price: TextView = itemView.tv_product_price
    val image: ImageView = itemView.iv_product
    val container: ConstraintLayout = itemView.cv_product
    val progressBar: ProgressBar = itemView.progress_circular

    var onItemClick: ((Results)-> Unit)? = null

    override fun bind(data: Results, position: Int) {
        title.text = data.title
        price.text = price.context.resources.getString(R.string.price_format, String.format(Locale.GERMAN,"%,2f", data.price.toDouble()).dropLast(4))
        val picasso = Picasso.get()
        picasso
            .load(data.thumbnail)
            .into(object : Target {
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    progressBar.visibility = View.GONE
                    image.setImageBitmap(bitmap)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            })

        container.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

}