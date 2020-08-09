package com.example.personalshop.model.search

import java.io.Serializable

class Results (

    val id : String,
    val site_id : String,
    val title : String,
    val seller : Seller,
    val price : String,
    val currency_id : String,
    val available_quantity : Int,
    val sold_quantity : Int,
    val buying_mode : String,
    val listing_type_id : String,
    val stop_time : String,
    val condition : String,
    val permalink : String,
    var thumbnail : String,
    val accepts_mercadopago : Boolean,
    val installments : Installments,
    val address : Address,
    val shipping : Shipping,
    val seller_address : Seller_address,
    val attributes : List<Attributes>,
    val original_price : String,
    val category_id : String,
    val official_store_id : String,
    val domain_id : String,
    val catalog_product_id : String,
    val tags : List<String>,
    val catalog_listing : Boolean
): Serializable