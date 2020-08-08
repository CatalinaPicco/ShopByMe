package com.example.personalshop.model.categories

import java.io.Serializable

class Category (
    val id : String,
    val name : String,
    var image : String? = null
): Serializable