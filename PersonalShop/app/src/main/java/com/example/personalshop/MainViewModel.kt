package com.example.personalshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.categories.CategoryDetail
import com.example.personalshop.model.detail.ProductDetailResponse
import com.example.personalshop.model.search.Results
import com.example.personalshop.model.search.SearchResponse
import com.example.personalshop.services.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    var query = MutableLiveData<String>()
    var result = MutableLiveData<List<Results>>()
    var categories = MutableLiveData<List<Category>>()
    var categoriesAux = emptyList<Category>()
    var productsAux = MutableLiveData<List<Results>>()
    var selectedCategory = MutableLiveData<Category?>()
    var onSearchClick: (() -> Unit)? = null
    var onEmpty: (() -> Unit)? = null
    var onCleaned: (() -> Unit)? = null
    var isLoading = MutableLiveData<Boolean>()
    var isAllLoaded = false
    var offset = 0
    var alreadyExecuted = false
    var disposable: Disposable? = null
    val itemsPerPage = 10
    private val searchService by lazy {
        SearchService.create()
    }

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

    fun fullCategories(result: CategoryDetail?) {
        categoriesAux.forEach {
           if (it.id == result?.id){
            it.image = result.picture
           }
        }
        categories.value = categoriesAux
    }

    private fun doPaginate(it: List<Results>) {
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
        query.value = null
        offset = 0
        isAllLoaded = false
    }

    fun emptyCategory() {
        selectedCategory.value = null
    }

    private fun fullImages(detail: ProductDetailResponse?) {
       result.value?.forEach {
           if (it.id == detail?.id){
               it.thumbnail = detail.pictures.get(0).url
           }
       }
    }

    private fun getImage(id: String) {
        disposable = searchService.getDetail(id, "all")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> run{
                    fullImages(result)
                }
                },
                { error -> println("error: " + error.message) }
            )
    }

    private fun beginSearch(searchString: String?, category: String?) {
        disposable = searchService.searchProducts(searchString, offset, itemsPerPage, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { SearchResponse ->
                    run {
                        if (SearchResponse.results.isEmpty() && query.value?.isNotEmpty()!!){
                            onEmpty?.invoke()
                        } else if (SearchResponse.results.isEmpty()) {
                            onCleaned?.invoke()
                        }
                        SearchResponse.results.forEach {
                            getImage(it.id)
                        }
                        doPaginate(SearchResponse.results)
                    }
                },
                { error -> println("error: " + error.message) }
            )
    }

    fun loadMore() {
        if (!isAllLoaded && !isLoading.value!!){
            offset += itemsPerPage
            beginSearch(query.value, selectedCategory.value?.id)
            isLoading.value = true
        }
    }

    fun onChangeQuery(query: String?) {
        result.value = emptyList()
        beginSearch(query, selectedCategory.value?.id)
    }

    fun onCategoryChange(category: Category?) {
        result.value = emptyList()
        emptyQuery()
        beginSearch("", category?.id)
    }

    fun getCategories() {
        disposable = searchService.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> run {
                    //viewModel?.categories?.value = result
                    categoriesAux = result
                    result.forEach {
                        getCategoryDetail(it.id)
                    }
                } },
                { error -> println("error: " + error.message) }
            )
    }

    private fun getCategoryDetail(categoryId: String) {
        disposable = searchService.getCategoriesDetail(categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> fullCategories(result) },
                { error -> println("error: " + error.message) }
            )
    }
    
}