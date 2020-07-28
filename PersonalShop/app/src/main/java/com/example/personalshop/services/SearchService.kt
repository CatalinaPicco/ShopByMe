package com.example.personalshop.services

import com.example.personalshop.model.categories.Category
import com.example.personalshop.model.description.DescriptionResponse
import com.example.personalshop.model.detail.ProductDetailResponse
import com.example.personalshop.model.search.SearchResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



interface SearchService {

    @GET("sites/MLA/categories")
    fun getCategories(
    ): Observable<List<Category>>

    @GET("sites/MLA/search?")
    fun searchProducts(
        @Query("q") query: String?,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("category") category: String?
    ): Observable<SearchResponse>

    @GET("items/{ITEM_ID}")
    fun getDetail(
        @Path("ITEM_ID") itemId: String,
        @Query("include_attributes") query: String?
    ): Observable<ProductDetailResponse>

    @GET("items/{ITEM_ID}/description")
    fun getDescription(
        @Path("ITEM_ID") itemId: String
    ): Observable<DescriptionResponse>

    companion object {
        fun create(): SearchService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.mercadolibre.com/")
                .client(httpClient.build())
                .build()

            return retrofit.create(SearchService::class.java)
        }
    }

}

