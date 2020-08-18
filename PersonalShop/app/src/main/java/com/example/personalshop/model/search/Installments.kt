package com.example.personalshop.model.search

import java.io.Serializable

class Installments(

    val quantity: Int,
    val amount: Double,
    val rate: Double,
    val currency_id: String
) : Serializable