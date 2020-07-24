package com.example.personalshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalshop.model.Results
import com.example.personalshop.model.SearchResponse

class MainViewModel : ViewModel() {

    var query = MutableLiveData<String>()
    var result = MutableLiveData<List<Results>>()

    init {

        if (query.value == null) {
            query.value = ""
        }

        if (result.value == null) {
            result.value = emptyList()
        }

    }

    fun setResults(search: SearchResponse){
        result.value = search.results
    }

}