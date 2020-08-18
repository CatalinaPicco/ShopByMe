package com.example.personalshop.model.search

import java.io.Serializable

class Paging(

    val total: Int,
    val offset: Int,
    val limit: Int,
    val primary_results: Int
) : Serializable