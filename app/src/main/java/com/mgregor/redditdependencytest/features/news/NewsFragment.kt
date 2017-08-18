package com.mgregor.redditdependencytest.features.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mgregor.redditdependencytest.R
import com.mgregor.redditdependencytest.RedditApp
import com.mgregor.redditdependencytest.api.NewsAPI
import com.mgregor.redditdependencytest.common.RedditNews
import com.mgregor.redditdependencytest.common.RedditNewsItem
import com.mgregor.redditdependencytest.common.RxBaseFragment
import com.mgregor.redditdependencytest.common.inflate
import com.mgregor.redditdependencytest.features.news.adapter.NewsAdapter
import com.mgregor.redditdependencytest.features.news.adapter.NewsDelegateAdapter
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.Orientation
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.news_fragment_new.*
import javax.inject.Inject

/**
 * Created by mgregor on 8/14/2017.
 *
 * NewsFragment Yay
 */
class NewsFragment : RxBaseFragment(), NewsDelegateAdapter.onViewSelectedListener {
	override fun onItemSelected(url: String?) {
		if (url.isNullOrEmpty()) {
			Snackbar.make(picker, "No URL assigned to this news", Snackbar.LENGTH_SHORT).show()
		} else {
			val intent = Intent(Intent.ACTION_VIEW)
			intent.data = Uri.parse(url)
			startActivity(intent)
		}
	}

	companion object {
		private val KEY_REDDIT_NEWS = "redditNews"
	}

	@Inject lateinit var newsManager: NewsManager
	@Inject lateinit var newsApi: NewsAPI
	private var redditNews: RedditNews? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		RedditApp.topNewsComponent.inject(this)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return container?.inflate(R.layout.news_fragment_new)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		/*news_list.apply {
			setHasFixedSize(true)
			val linearLayoutManager = LinearLayoutManager(context)
			layoutManager = linearLayoutManager
			clearOnScrollListeners()
			addOnScrollListener(InfiniteScrollListener({ requestItems() }, linearLayoutManager))
			initAdapter()
		}*/

		picker.apply {
			setOrientation(Orientation.VERTICAL)
			setItemTransformer(ScaleTransformer.Builder().setMinScale(0.8f).build())

			addScrollListener(object : DiscreteScrollView.ScrollListener<RecyclerView.ViewHolder> {
				private var loading = true
				private var previousTotal = 0

				override fun onScroll(scrollPosition: Float, currentHolder: RecyclerView.ViewHolder, newCurrent: RecyclerView.ViewHolder) {
					if (loading) {
						if (layoutManager.itemCount > previousTotal) {
							previousTotal = layoutManager.itemCount
							loading = false
						}
					}

					if (!loading && layoutManager.itemCount == newCurrent.adapterPosition + 1) {
						loading = true
						requestItems(false)
						Log.e("tag", "requestedItems")
					}
				}
			})
		}

		initAdapter()

		if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
			redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
			(picker.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
		} else {
			requestItems(true)
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		val news = (picker.adapter as NewsAdapter).getNews()
		if (redditNews != null && news.isNotEmpty()) {
			outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
		}
	}

	private fun requestItems(firstTime: Boolean) {
		/*val subscription = newsManager.getUserSubmitted("Aenami", redditNews?.after ?: "")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						{
							retrievedNews ->
							redditNews = retrievedNews
							(news_list.adapter as NewsAdapter).addNews(retrievedNews.news)
						},
						{
							e ->
							Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_SHORT).show()
						}
				)*/

		val newSubscription = newsApi.getUserSubmitted("Aenami", redditNews?.after ?: "")
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(
						{ newsResponse ->
							val news = newsResponse.data.children.map {
								val newsItem = it.data
								RedditNewsItem(newsItem.author, newsItem.title, newsItem.num_comments,
										newsItem.created, newsItem.thumbnail, newsItem.url)
							}
							redditNews = RedditNews(
									newsResponse.data.after ?: "",
									newsResponse.data.before ?: "",
									news
							)
							redditNews?.apply {
								(picker.adapter as NewsAdapter).addNews(this.news)
							}
							if (firstTime) picker.smoothScrollToPosition(1)
						},
						{ e ->
							Snackbar.make(picker, e.message ?: "", Snackbar.LENGTH_SHORT).show()
						}
				)

		subscriptions.add(newSubscription)
	}

	private fun initAdapter() {
		if (picker.adapter == null) {
			picker.adapter = NewsAdapter(this)
		}
	}
}