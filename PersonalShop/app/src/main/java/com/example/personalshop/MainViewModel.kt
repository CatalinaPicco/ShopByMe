package com.example.personalshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.search.Results
import com.example.personalshop.model.search.SearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel : ViewModel() {

    var query = MutableLiveData<String>()
    var result = MutableLiveData<List<Results>>()
    var categories: List<Category> = emptyList()
    var selectedCategory = MutableLiveData<String?>()

    init {

        if (query.value == null) {
            query.value = ""
        }

        if (result.value == null) {
            result.value = emptyList()
        }

        if (selectedCategory.value == null) {
            selectedCategory.value = ""
        }

    }

    fun setResults(search: SearchResponse){
        result.value = search.results
    }

}