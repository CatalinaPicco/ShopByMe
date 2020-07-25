package com.example.personalshop.model.search

data class Seller_address (

    val id : String,
    val comment : String,
    val address_line : String,
    val zip_code : String,
    val country : Country,
    val state : State,
    val city : City,
    val latitude : String,
    val longitude : String
)