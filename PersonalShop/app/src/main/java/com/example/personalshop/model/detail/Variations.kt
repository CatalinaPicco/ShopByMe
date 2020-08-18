package com.example.personalshop.model.detail

class Variations(

    val id: String,
    val price: String,
    val attribute_combinations: List<Attribute_combinations>,
    val available_quantity: Int,
    val sold_quantity: Int,
    val sale_terms: List<Any>,
    val picture_ids: List<String>,
    val catalog_product_id: String,
    val attributes: List<Attributes>
)