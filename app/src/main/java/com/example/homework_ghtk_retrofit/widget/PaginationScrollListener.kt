package com.example.examplerecyclerview.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private var linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val  visibleItemCount : Int = linearLayoutManager.childCount
        val  totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
         if(isLoading()||isLastPage()){
            return
        }
        if(firstVisibleItemPosition>=0 && (visibleItemCount+firstVisibleItemPosition)>=totalItemCount){
            loadMoreItem()

        }
    }
    abstract fun loadMoreItem()
    abstract fun isLoading() : Boolean
    abstract fun isLastPage() : Boolean

}