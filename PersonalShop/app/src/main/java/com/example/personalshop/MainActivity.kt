package com.example.personalshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.personalshop.home.HomeResultsFragment
import com.example.personalshop.services.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {
                //beginSearch(edit_search.text.toString())
            }
        }

        showFragment(HomeResultsFragment())
    }

    private fun showFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction =  manager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

}
