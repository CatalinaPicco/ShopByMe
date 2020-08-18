package com.example.personalshop.model.search

import java.io.Serializable

class Ratings(

    val negative: Double,
    val positive: Double,
    val neutral: Double
) : Serializable