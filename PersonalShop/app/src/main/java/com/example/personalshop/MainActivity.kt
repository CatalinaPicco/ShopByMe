package com.example.personalshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personalshop.services.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val searchService by lazy {
        SearchService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {
                beginSearch(edit_search.text.toString())
            }
        }
    }



    private fun beginSearch(searchString: String) {
        disposable = searchService.searchProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> println("resultados: " + result.toString())},
                { error -> println("error: " + error.message) }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
