package com.mgregor.redditdependencytest.di.topNews

import com.mgregor.redditdependencytest.di.AppModule
import com.mgregor.redditdependencytest.di.NetworkModule
import com.mgregor.redditdependencytest.features.news.NewsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by mgregor on 8/14/2017.
 */
@Singleton
@Component(modules = arrayOf(
		AppModule::class,
		TopNewsModule::class,
		NetworkModule::class
))
interface TopNewsComponent {
	fun inject(newsFragment: NewsFragment)
}