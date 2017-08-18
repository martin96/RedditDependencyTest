package com.mgregor.redditdependencytest.api

/**
 * Created by mgregor on 8/14/2017.
 */

class RedditNewsResponse(val data: RedditDataResponse)

class RedditDataResponse(
		val children: List<RedditChildrenResponse>,
		val after: String?,
		val before: String?
)

class RedditChildrenResponse(
		val kind: String,
		val data: RedditNewsDataResponse
)

class RedditNewsDataResponse(
		val author: String,
		val title: String,
		val num_comments: Int,
		val created: Long,
		val thumbnail: String,
		val url: String?
)