package com.example.personalshop.model

data class Filters (

	val id : String,
	val name : String,
	val type : String,
	val values : List<Values>
)