package com.example.personalshop.model.search

import java.io.Serializable

class Transactions(

    val total: Int,
    val canceled: Int,
    val period: String,
    val ratings: Ratings,
    val completed: Int
) : Serializable