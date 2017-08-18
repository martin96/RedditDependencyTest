package com.mgregor.redditdependencytest.di.topNews

import com.mgregor.redditdependencytest.api.NewsAPI
import com.mgregor.redditdependencytest.api.RedditAPI
import com.mgregor.redditdependencytest.api.NewsRestAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by mgregor on 8/14/2017.
 */
@Module
class TopNewsModule {

	@Provides
	@Singleton
	fun provideRedditApi(retrofit: Retrofit): RedditAPI = retrofit.create(RedditAPI::class.java)

	@Provides
	@Singleton
	fun provideNewsAPI(redditAPI: RedditAPI): NewsAPI = NewsRestAPI(redditAPI)
}