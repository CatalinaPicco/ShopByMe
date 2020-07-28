package com.example.personalshop.model.detail

class Shipping (

	val mode : String,
	val free_methods : List<Free_methods>,
	val tags : List<String>,
	val dimensions : String,
	val local_pick_up : Boolean,
	val free_shipping : Boolean,
	val logistic_type : String,
	val store_pick_up : Boolean
)