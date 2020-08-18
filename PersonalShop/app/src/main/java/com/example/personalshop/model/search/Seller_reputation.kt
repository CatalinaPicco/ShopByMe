package com.example.personalshop.model.search

import java.io.Serializable

class Seller_reputation(

    val transactions: Transactions,
    val power_seller_status: String,
    val metrics: Metrics,
    val level_id: String
) : Serializable