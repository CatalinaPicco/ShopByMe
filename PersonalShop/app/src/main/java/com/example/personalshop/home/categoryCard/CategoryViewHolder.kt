package com.example.personalshop.home.categoryCard

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.detail.Pictures
import com.example.personalshop.utils.GenericAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.producto_image_layout.view.*
import kotlinx.android.synthetic.main.producto_image_layout.view.ll_image_placeholder

class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), GenericAdapter.Binder<Category> {

    val title: TextView = itemView.tv_category_title
    val container: ConstraintLayout = itemView.ll_category

    var onItemClick: ((Category)-> Unit)? = null

    override fun bind(data: Category, position: Int) {
        title.text = data.name?:""
        container.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

}