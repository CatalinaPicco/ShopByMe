package com.example.personalshop.model.search

data class Filters (

	val id : String,
	val name : String,
	val type : String,
	val values : List<Values>
)