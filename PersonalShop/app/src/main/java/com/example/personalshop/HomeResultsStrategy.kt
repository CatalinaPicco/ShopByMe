package com.example.personalshop

import com.example.personalshop.home.productCard.ProductoViewType
import com.example.personalshop.home.strategyRecycler.AbstractStrategyCard
import com.example.personalshop.home.strategyRecycler.ICardItem
import java.lang.Exception

class HomeResultsStrategy: AbstractStrategyCard() {
    override fun setStrategy(iCardItem: ICardItem) {
        setIViewType(
            when (iCardItem.type) {
                ICardItem.Type.PRODUCTO -> ProductoViewType()
                else -> throw Exception("no viewtypes")
        })
    }
}