package com.mgregor.redditdependencytest.features.news

import com.mgregor.redditdependencytest.api.NewsAPI
import com.mgregor.redditdependencytest.api.RedditNewsResponse
import com.mgregor.redditdependencytest.common.RedditNews
import com.mgregor.redditdependencytest.common.RedditNewsItem
import io.reactivex.Observable
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by mgregor on 8/14/2017.
 */
@Singleton
class NewsManager @Inject constructor(private val api: NewsAPI) {

	fun getTopNews(after: String, limit: String = "10"): Observable<RedditNews> {
		return Observable.create {
			subscriber ->
			val callResponse: Call<RedditNewsResponse> = api.getTopNews(after, limit)
			val response = callResponse.execute()

			if (response.isSuccessful) {
				val dataResponse = response.body()?.data
				if (dataResponse != null) {
					val news = dataResponse.children.map {
						val item = it.data
						RedditNewsItem(item.author, item.title, item.num_comments,
								item.created, item.thumbnail, item.url)
					}
					val redditNews = RedditNews(
							dataResponse.after ?: "",
							dataResponse.before ?: "",
							news)
					subscriber.onNext(redditNews)
					subscriber.onComplete()
				} else {
					subscriber.onError(Throwable(response.message()))
				}
			} else {
				subscriber.onError(Throwable(response.message()))
			}
		}
	}

	/*fun getUserSubmitted(username: String, after: String, limit: String = "10"): Observable<RedditNews> {
		return Observable.create {
			subscriber ->
			val callResponse: Call<RedditNewsResponse> = api.getUserSubmitted(username, after, limit)
			val response = callResponse.execute()

			if (response.isSuccessful) {
				val dataResponse = response.body()?.data
				if (dataResponse != null) {
					val news = dataResponse.children.map {
						val item = it.data
						RedditNewsItem(item.author, item.title, item.num_comments,
								item.created, item.thumbnail, item.url)
					}
					val redditNews = RedditNews(
							dataResponse.after ?: "",
							dataResponse.before ?: "",
							news)
					subscriber.onNext(redditNews)
					subscriber.onComplete()
				} else {
					subscriber.onError(Throwable(response.message()))
				}
			} else {
				subscriber.onError(Throwable(response.message()))
			}
		}
	}*/
}