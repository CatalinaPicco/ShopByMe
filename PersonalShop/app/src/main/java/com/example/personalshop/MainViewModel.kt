package com.example.personalshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.categories.CategoryDetail
import com.example.personalshop.model.detail.ProductDetailResponse
import com.example.personalshop.model.search.Results
import com.example.personalshop.model.search.SearchResponse

class MainViewModel : ViewModel() {

    var query = MutableLiveData<String>()
    var result = MutableLiveData<List<Results>>()
    var categories = MutableLiveData<List<Category>>()
    var categoriesAux = emptyList<Category>()
    var productsAux = MutableLiveData<List<Results>>()
    var selectedCategory = MutableLiveData<Category?>()
    var onSearchClick: (() -> Unit)? = null
    var isLoading = MutableLiveData<Boolean>()
    var isAllLoaded = false
    var offset = 0

    init {

        if (query.value == null) {
            query.value = ""
        }

        if (isLoading.value == null) {
            isLoading.value = false
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

    fun setResultsValue(search: List<Results>){
        result.value = search
    }

    fun fullCategories(result: CategoryDetail?) {
        categoriesAux.forEach {
           if (it.id == result?.id){
            it.image = result.picture
           }
        }
        categories.value = categoriesAux
    }

    fun doStuff(it: List<Results>) {
        isLoading.value = false
        if (result.value.isNullOrEmpty()){
            result.value = it
        } else {
            val auxList: MutableList<Results> = result.value as MutableList<Results>
            auxList.addAll(it)
            if (it.size < 10){
                isAllLoaded = true
            }
            result.value = auxList
        }
    }

    fun emptyQuery() {
        offset = 0
        isAllLoaded = false
    }

    fun fullImages(detail: ProductDetailResponse?) {
       result.value?.forEach {
           if (it.id == detail?.id){
               it.thumbnail = detail.pictures.get(0).url
           }
       }
    }

}