package com.example.personalshop.model

data class Eshop (

	val nick_name : String,
	val eshop_rubro : Any,
	val eshop_id : Int,
	val eshop_locations : List<String>,
	val site_id : String,
	val eshop_logo_url : String,
	val eshop_status_id : Int,
	val seller : Int,
	val eshop_experience : Int
)