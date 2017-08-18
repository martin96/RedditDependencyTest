package com.mgregor.redditdependencytest.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mgregor.redditdependencytest.R
import com.mgregor.redditdependencytest.common.adapter.ViewType
import com.mgregor.redditdependencytest.common.adapter.ViewTypeDelegateAdapter
import com.mgregor.redditdependencytest.common.inflate

/**
 * Created by mgregor on 8/14/2017.
 */
class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
	override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {}

	inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
			parent.inflate(R.layout.news_item_loading_new))
}