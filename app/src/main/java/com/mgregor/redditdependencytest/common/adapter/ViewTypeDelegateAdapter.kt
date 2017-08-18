package com.mgregor.redditdependencytest.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by mgregor on 8/14/2017.
 */
interface ViewTypeDelegateAdapter {
	fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

	fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}