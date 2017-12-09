package com.suhel.photosphere.Fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.suhel.photosphere.DataModels.Adapters.Derived.PostsAdapter
import com.suhel.photosphere.Singleton.FIELD_POSTS_ROOT
import com.suhel.photosphere.Helper.Actions
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.database
import kotlinx.android.synthetic.main.fragment_timeline.*

class TimelineFragment(val title: String = "timeline", val sortBy: String = "", val ascending: Boolean = false) :
        BaseFragment(R.layout.fragment_timeline) {

    private val postsAdapter by lazy {
        if (sortBy == "") {
            PostsAdapter(database.getReference(FIELD_POSTS_ROOT), ascending)
        } else {
            PostsAdapter(database.getReference(FIELD_POSTS_ROOT).orderByChild(sortBy), ascending)
        }
    }

    private var currentCompetitionId: String? = null

    override fun onViewsCreated() {

        fragTimelineList?.layoutManager = LinearLayoutManager(context)
        fragTimelineList?.adapter = postsAdapter
        fragTimelineList?.addOnScrollListener(scrollListener)

        fabCamera.setOnClickListener {
            callback.onAction(Actions.ACTION_CHOOSE_EXTERNAL_CAMERA)
        }

        fabGallery.setOnClickListener {
            callback.onAction(Actions.ACTION_CHOOSE_GALLERY)
        }
    }

    fun setCurrentCompetition(competitionId: String) {
        currentCompetitionId = competitionId
    }

    override fun onResume() {
        super.onResume()
        callback.showAppBar(true)
        callback.setTitle(title)
        postsAdapter.attachDataSource()
    }

    override fun onPause() {
        super.onPause()
        postsAdapter.detachDataSource()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 10) {
                callback.setTitleVisibility(false)
                fragTimelineFab.hideMenu(true)
            } else if (dy < -10) {
                callback.setTitleVisibility(true)
                fragTimelineFab.showMenu(true)
            }
        }
    }

}