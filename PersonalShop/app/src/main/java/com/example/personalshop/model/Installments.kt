package com.example.personalshop.model

data class Installments (

	val quantity : Int,
	val amount : Double,
	val rate : Double,
	val currency_id : String
)