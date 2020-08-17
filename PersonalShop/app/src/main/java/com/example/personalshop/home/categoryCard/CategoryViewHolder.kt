package com.example.personalshop.home.categoryCard

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.R
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.detail.Pictures
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.producto_image_layout.view.*
import kotlinx.android.synthetic.main.producto_image_layout.view.ll_image_placeholder
import java.lang.Exception

class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Category> {

    val title: TextView = itemView.tv_category_title
    val image: ImageView = itemView.iv_category
    val container: ConstraintLayout = itemView.ll_category
    val progressBar: ProgressBar = itemView.pb_category_loader

    var onItemClick: ((Category)-> Unit)? = null

    override fun bind(data: Category, position: Int) {
        title.text = data.name?:""
        val picasso = Picasso.get()
        if (!data.image.isNullOrEmpty()){
            picasso.load(data.image)
                .into(object : Target {
                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            container.visibility = View.GONE
                        }
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            image.setImageBitmap(bitmap)
                            progressBar.visibility = View.GONE
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            
                        }

                    })
        }
        container.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

}