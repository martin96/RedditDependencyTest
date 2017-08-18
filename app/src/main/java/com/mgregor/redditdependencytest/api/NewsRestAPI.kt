package com.mgregor.redditdependencytest.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by mgregor on 8/14/2017.
 */
class NewsRestAPI @Inject constructor(private val redditAPI: RedditAPI) : NewsAPI {
	override fun getHotNews(after: String, limit: String): Observable<RedditNewsResponse> {
		return redditAPI.getHotNews(after, limit)
	}

	override fun getTopNews(after: String, limit: String): Call<RedditNewsResponse> {
		return redditAPI.getTopNews(after, limit)
	}

	override fun getUserSubmitted(username: String, after: String, limit: String): Single<RedditNewsResponse> {
		return redditAPI.getUserSubmitted(username, after, limit)
	}
}