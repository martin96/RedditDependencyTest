package com.mgregor.redditdependencytest.di

import android.app.Application
import android.content.Context
import com.mgregor.redditdependencytest.RedditApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by mgregor on 8/14/2017.
 */
@Module
class AppModule(val app: RedditApp) {

	@Provides
	@Singleton
	fun provideContext(): Context = app

	@Provides
	@Singleton
	fun provideApplication(): Application = app
}