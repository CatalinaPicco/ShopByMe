package com.example.personalshop.model.search

import java.io.Serializable

class SearchResponse (
    val site_id : String,
    val paging : Paging,
    val results : List<Results>,
    val secondary_results : List<String>,
    val related_results : List<String>,
    val sort : Sort,
    val available_sorts : List<Available_sorts>,
    val filters : List<Filters>,
    val available_filters : List<Available_filters>
): Serializable