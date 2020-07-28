package com.example.personalshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.search.Results
import com.example.personalshop.model.search.SearchResponse

class MainViewModel : ViewModel() {

    var query = MutableLiveData<String>()
    var result = MutableLiveData<List<Results>>()
    var categories = MutableLiveData<List<Category>>()
    var selectedCategory = MutableLiveData<Category?>()
    var onSearchClick: (() -> Unit)? = null

    init {

        if (query.value == null) {
            query.value = ""
        }

        if (result.value == null) {
            result.value = emptyList()
        }

        if (categories.value == null) {
            categories.value = emptyList()
        }

    }

    fun setResults(search: SearchResponse){
        result.value = search.results
    }

 }