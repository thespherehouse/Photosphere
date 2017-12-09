package com.suhel.photosphere.Fragments

import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.suhel.photosphere.Singleton.FIELD_COMPETITIONS_ROOT
import com.suhel.photosphere.Singleton.FIELD_COMPETITION_HOST_ID
import com.suhel.photosphere.Helper.loadFragmentWithFade
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.choose_competition.*

class ChooseCompetition : BaseFragment(R.layout.choose_competition) {

    private val listOfCompetitions = CompetitionsFragment()

    override fun onViewsCreated() {
        bChooseCompetitionCreated.setOnClickListener {
            listOfCompetitions.setQuery(FirebaseDatabase.getInstance().reference.child(FIELD_COMPETITIONS_ROOT)
                    .orderByChild(FIELD_COMPETITION_HOST_ID).equalTo(currentUser?.uid))
            loadFragmentWithFade(R.id.mainFrame, listOfCompetitions, true, true)
        }

        bChooseCompetitionParticipated.setOnClickListener {
            Toast.makeText(context, "Sorry, not implemented yet, search in All", Toast.LENGTH_SHORT).show()
        }

        bChooseCompetitionAll.setOnClickListener {
            listOfCompetitions.removeQuery()
            loadFragmentWithFade(R.id.mainFrame, listOfCompetitions, true, true)
        }
    }

    override fun onResume() {
        super.onResume()
        callback.setAppFullscreen(false)
        callback.showAppBar(true)
        callback.setTitle("competitions")
    }

}