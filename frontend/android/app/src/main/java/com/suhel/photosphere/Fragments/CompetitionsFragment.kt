package com.suhel.photosphere.Fragments

import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.suhel.photosphere.DataModels.Adapters.Derived.CompetitionsAdapter
import com.suhel.photosphere.Singleton.FIELD_COMPETITIONS_ROOT
import com.suhel.photosphere.Helper.dialogAddCompetition
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.fragment_competitions.*

class CompetitionsFragment : BaseFragment(R.layout.fragment_competitions) {

    private val competitionsAdapter by lazy {
        CompetitionsAdapter(FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT))
    }

    override fun onViewsCreated() {
        fragTimelineList.layoutManager = LinearLayoutManager(context)
        fragTimelineList.adapter = competitionsAdapter

        fabTimelineCompetitions.setOnClickListener {
            dialogAddCompetition(context)
        }
    }

    override fun onResume() {
        super.onResume()
        competitionsAdapter.attachDataSource()
        callback.setAppFullscreen(false)
        callback.showAppBar(true)
        callback.setTitle("competitions")
    }

    override fun onPause() {
        super.onPause()
        competitionsAdapter.detachDataSource()
    }

}