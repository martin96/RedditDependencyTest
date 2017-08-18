package com.mgregor.redditdependencytest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import com.mgregor.redditdependencytest.features.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val toolbar = findViewById<Toolbar>(R.id.toolbar)
		setSupportActionBar(toolbar)

		if (savedInstanceState == null) {
			changeFragment(NewsFragment())
		}

		category.setOnClickListener { view ->
			val popup = PopupMenu(this, view)
			val menuInflater = popup.menuInflater
			menuInflater.inflate(R.menu.actions, popup.menu)
			popup.show()
		}
	}

	fun changeFragment(fragment: Fragment) {
		val ft = supportFragmentManager.beginTransaction()
		ft.replace(R.id.main_content, fragment)
		ft.commit()
	}
}