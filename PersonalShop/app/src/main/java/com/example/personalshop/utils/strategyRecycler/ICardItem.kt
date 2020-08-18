package com.example.personalshop.utils.strategyRecycler

import java.io.Serializable

interface ICardItem : Serializable {

    val type: Type

    enum class Type(val id: Int) {
        CATEGORIA(0),
        TIENDA_OFICIAL(1),
        VENDEDOR(2),
        PRODUCTO(3),
        OFERTA(4),
        BANNER(5),
        CAROUSEL(6)
    }
}