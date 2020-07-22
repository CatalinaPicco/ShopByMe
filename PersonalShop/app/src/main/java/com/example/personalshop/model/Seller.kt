package com.example.personalshop.model

data class Seller (

	val id : Int,
	val permalink : String,
	val registration_date : String,
	val car_dealer : Boolean,
	val real_estate_agency : Boolean,
	val tags : List<String>,
	val eshop : Eshop,
	val seller_reputation : Seller_reputation
)