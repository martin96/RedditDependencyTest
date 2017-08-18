package com.mgregor.redditdependencytest.api

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by mgregor on 8/14/2017.
 */
interface RedditAPI {

	@GET("./hot.json")
	fun getHotNews(@Query("after") after: String,
				   @Query("limit") limit: String): Observable<RedditNewsResponse>

	@GET("./top.json")
	fun getTopNews(@Query("after") after: String,
				   @Query("limit") limit: String): Call<RedditNewsResponse>

	@GET("./user/{username}/submitted.json")
	fun getUserSubmitted(@Path("username") username: String,
						 @Query("after") after: String,
						 @Query("limit") limit: String): Single<RedditNewsResponse>
}