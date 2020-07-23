package com.example.personalshop.services

import com.example.personalshop.model.SearchResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("sites/MLA/search?")
    fun searchProducts(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<SearchResponse>

    companion object {
        fun create(): SearchService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.mercadolibre.com/")
                .build()

            return retrofit.create(SearchService::class.java)
        }
    }

}

