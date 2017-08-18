package com.mgregor.redditdependencytest.features.news.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mgregor.redditdependencytest.common.RedditNewsItem
import com.mgregor.redditdependencytest.common.adapter.AdapterConstants
import com.mgregor.redditdependencytest.common.adapter.ViewType
import com.mgregor.redditdependencytest.common.adapter.ViewTypeDelegateAdapter

/**
 * Created by mgregor on 8/14/2017.
 */
class NewsAdapter(listener: NewsDelegateAdapter.onViewSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	private val items: ArrayList<ViewType>
	private val delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
	private val loadingItem = object : ViewType {
		override fun getViewType() = AdapterConstants.LOADING
	}

	init {
		delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
		delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter(listener))
		items = ArrayList()
		items.add(loadingItem)
	}

	override fun getItemCount() = items.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = delegateAdapters.get(viewType).onCreateViewHolder(parent)

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
	}

	override fun getItemViewType(position: Int) = items[position].getViewType()

	fun addNews(news: List<RedditNewsItem>) {
		val initPosition = items.size - 1
		items.removeAt(initPosition)
		notifyItemRemoved(initPosition)

		items.addAll(news)
		items.add(loadingItem)
		notifyItemRangeChanged(initPosition, news.size + 1)
	}

	fun clearAndAddNews(news: List<RedditNewsItem>) {
		items.clear()
		notifyItemRangeRemoved(0, getLastPosition())

		items.addAll(news)
		items.add(loadingItem)
		notifyItemRangeRemoved(0, items.size)
	}

	fun getNews(): List<RedditNewsItem> =
			items.filter { it.getViewType() == AdapterConstants.NEWS }
					.map { it as RedditNewsItem }

	private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}