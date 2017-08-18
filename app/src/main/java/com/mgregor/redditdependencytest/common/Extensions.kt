package com.mgregor.redditdependencytest.common

import android.database.sqlite.SQLiteDatabase
import android.os.Trace
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mgregor.redditdependencytest.R

/**
 * Created by mgregor on 8/14/2017.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
	return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String) {
	if (TextUtils.isEmpty(imageUrl)) {
		Glide.with(context).load(R.mipmap.ic_launcher).into(this)
	} else {
		Glide.with(context).load(imageUrl).into(this)
	}
}

//----------------------------------------------------------------

inline fun ViewGroup.forEachIndexed(action: (Int, View) -> Unit) {
	for (index in 0 until childCount) {
		action(index, getChildAt(index))
	}

	for (view in children()) {

	}

	val visibleHeight = children()
			.filter { it.visibility == View.VISIBLE }
			.sumBy { it.measuredHeight }
}

fun ViewGroup.children() = object : Iterable<View> {
	override fun iterator() = object : Iterator<View> {
		var index = 0
		override fun hasNext() = index < childCount
		override fun next() = getChildAt(index++)
	}
}

inline fun <T> trace(sectionName: String, body: () -> T): T {
	Trace.beginSection(sectionName)
	try {
		return body()
	} finally {
		Trace.endSection()
	}
}

inline fun SQLiteDatabase.transaction(body: SQLiteDatabase.() -> Unit) {
	beginTransaction()
	try {
		body()
		setTransactionSuccessful()
	} finally {
		endTransaction()
	}
}