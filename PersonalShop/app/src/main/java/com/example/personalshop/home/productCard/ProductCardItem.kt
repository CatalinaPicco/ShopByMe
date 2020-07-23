package com.example.personalshop.home.productCard

import com.example.personalshop.home.strategyRecycler.ICardItem
import com.example.personalshop.model.Results

class ProductCardItem(result: Results): ICardItem {
    override val type: ICardItem.Type
    get() = ICardItem.Type.PRODUCTO
    val data = result
}