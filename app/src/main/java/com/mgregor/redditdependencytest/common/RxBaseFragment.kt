package com.mgregor.redditdependencytest.common

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by mgregor on 8/14/2017.
 */
open class RxBaseFragment : Fragment() {
	internal var subscriptions = CompositeDisposable()

	override fun onResume() {
		super.onResume()
		subscriptions = CompositeDisposable()
	}

	override fun onPause() {
		super.onPause()
		subscriptions.clear()
	}
}