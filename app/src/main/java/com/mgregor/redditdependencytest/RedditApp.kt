package com.mgregor.redditdependencytest

import android.app.Application
import com.mgregor.redditdependencytest.di.topNews.DaggerTopNewsComponent
import com.mgregor.redditdependencytest.di.topNews.TopNewsComponent

/**
 * Created by mgregor on 8/14/2017.
 */
class RedditApp : Application() {

	companion object {
		lateinit var topNewsComponent: TopNewsComponent
	}

	override fun onCreate() {
		super.onCreate()
		topNewsComponent = DaggerTopNewsComponent.builder()
				.build()
	}
}