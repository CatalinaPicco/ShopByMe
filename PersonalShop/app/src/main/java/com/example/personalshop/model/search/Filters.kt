package com.example.personalshop.model.search

import java.io.Serializable

class Filters (

	val id : String,
	val name : String,
	val type : String,
	val values : List<Values>
): Serializable