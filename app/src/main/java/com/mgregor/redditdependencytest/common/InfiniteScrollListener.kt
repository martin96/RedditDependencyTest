package com.mgregor.redditdependencytest.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by mgregor on 8/15/2017.
 */
class InfiniteScrollListener(
		val func: () -> Unit,
		val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

	private var previousTotal = 0
	private var loading = true

	override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
		super.onScrolled(recyclerView, dx, dy)

		Log.e("InfiniteScrollListener", "ItemCount: ${layoutManager.itemCount}, LastVisible: ${layoutManager.findLastVisibleItemPosition()}")

		if (dy >= 0) {
			if (loading) {
				if (layoutManager.itemCount > previousTotal) {
					previousTotal = layoutManager.itemCount
					loading = false
				}
			}

			if (!loading && (layoutManager.itemCount - layoutManager.findLastVisibleItemPosition()) <= 1) {
				Log.e("InfiniteScrollListener", "End reached!")
				func()
				loading = true
			}
		}
	}
}