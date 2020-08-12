package com.example.personalshop.home.productCard

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import com.example.personalshop.model.detail.Pictures
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.producto_image_layout.view.*
import java.lang.Exception

class ProductImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Pictures> {

    val image: ImageView = itemView.iv_placeholder
    val container: CardView = itemView.ll_image_placeholder
    val progressBar: ProgressBar = itemView.pb_loader

    var onItemClick: ((Pictures)-> Unit)? = null

    override fun bind(data: Pictures, position: Int) {
        val picasso = Picasso.get()
        picasso.load(data.url)
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