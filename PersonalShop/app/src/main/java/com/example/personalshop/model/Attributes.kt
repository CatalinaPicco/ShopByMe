package com.example.personalshop.model

data class Attributes (

	val source : String,
	val name : String,
	val value_name : String,
	val values : List<Values>,
	val attribute_group_id : String,
	val id : String,
	val value_id : Int,
	val value_struct : String,
	val attribute_group_name : String
)