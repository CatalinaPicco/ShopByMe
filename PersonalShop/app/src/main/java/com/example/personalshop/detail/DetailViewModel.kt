package com.example.personalshop.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalshop.model.description.DescriptionResponse
import com.example.personalshop.model.detail.ProductDetailResponse
import java.util.*

class DetailViewModel : ViewModel() {

    lateinit var productId: String
    var productDetail = MutableLiveData<ProductDetailResponse>()
    var productDescription = MutableLiveData<DescriptionResponse>()

    init {

    }

    fun setResults(detailResponse: ProductDetailResponse?) {
        productDetail.value = detailResponse
    }

    fun setDescription(descriptionResponse: DescriptionResponse?) {
        productDescription.value = descriptionResponse
    }

}