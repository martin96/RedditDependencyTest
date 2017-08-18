package com.mgregor.redditdependencytest.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call

/**
 * Created by mgregor on 8/14/2017.
 */
interface NewsAPI {
	fun getHotNews(after: String, limit: String = "10"): Observable<RedditNewsResponse>

	fun getTopNews(after: String, limit: String): Call<RedditNewsResponse>

	fun getUserSubmitted(username: String, after: String, limit: String = "5"): Single<RedditNewsResponse>
}