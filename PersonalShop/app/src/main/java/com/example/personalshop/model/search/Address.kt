package com.example.personalshop.model.search

import java.io.Serializable

class Address(

    val state_id: String,
    val state_name: String,
    val city_id: String,
    val city_name: String
) : Serializable