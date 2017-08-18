package com.mgregor.redditdependencytest.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mgregor.redditdependencytest.R
import com.mgregor.redditdependencytest.common.RedditNewsItem
import com.mgregor.redditdependencytest.common.adapter.ViewType
import com.mgregor.redditdependencytest.common.adapter.ViewTypeDelegateAdapter
import com.mgregor.redditdependencytest.common.inflate
import com.mgregor.redditdependencytest.common.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

/**
 * Created by mgregor on 8/14/2017.
 */
class NewsDelegateAdapter(val viewActions: onViewSelectedListener) : ViewTypeDelegateAdapter {

	interface onViewSelectedListener {
		fun onItemSelected(url: String?)
	}

	override fun onCreateViewHolder(parent: ViewGroup) = NewsViewHolder(parent)

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
		holder as NewsViewHolder
		holder.bind(item as RedditNewsItem)
	}

	inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
			parent.inflate(R.layout.news_item_new)) {

		fun bind(item: RedditNewsItem) = with(itemView) {
			img_thumbnail.loadImg(item.url!!)
			description.text = item.title
			author.text = item.author
			comments.text = "${item.numComments} comments"
			time.text = item.created.toString()

			super.itemView.setOnClickListener { viewActions.onItemSelected(item.url) }
		}
	}
}