package com.example.personalshop.model.search

import java.io.Serializable

class Available_filters (

	val id : String,
	val name : String,
	val type : String,
	val values : List<Values>
): Serializable