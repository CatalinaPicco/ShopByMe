package com.example.personalshop.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener : RecyclerView.OnScrollListener {

    private var visibleThreshold = 10
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startPageIndex = 0

    internal val mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: RecyclerView.LayoutManager) {
        this.mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }


    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (dy < 1)
        {
            // don't do anything when scrolling up.
            return;
        }

        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startPageIndex
            this.previousTotalItemCount = totalItemCount
            if(totalItemCount == 0){
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)

}