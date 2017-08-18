package com.mgregor.redditdependencytest.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by mgregor on 8/14/2017.
 */
@Module
class NetworkModule {

	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit {
		return Retrofit.Builder()
				.baseUrl("https://www.reddit.com")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build()
	}
}