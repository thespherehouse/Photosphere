package com.suhel.photosphere.DataModels.Adapters.Derived

import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import com.suhel.photosphere.DataModels.Adapters.BaseAdapter
import com.suhel.photosphere.DataModels.Types.Derived.Competition
import com.suhel.photosphere.Fragments.TimelineFragment
import com.suhel.photosphere.Singleton.activity
import com.suhel.photosphere.Helper.loadFragmentWithFade
import com.suhel.photosphere.R
import com.suhel.photosphere.Views.ViewHolders.Derived.CompetitionViewHolder
import java.text.SimpleDateFormat
import java.util.*

class CompetitionsAdapter<out T : Query>(query: T) : BaseAdapter<T, Competition, CompetitionViewHolder>(query) {

    private val random = Random()
    private val filteredPostTimeline = TimelineFragment()

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val competition = data[position]

        holder.imgCompetitionCover?.setBackgroundColor(Color.argb(255, 127 + random.nextInt(128),
                127 + random.nextInt(128), 127 + random.nextInt(128)))

        holder.itemView.setOnClickListener {
            filteredPostTimeline.setCurrentCompetition(competition.id)
            loadFragmentWithFade(R.id.mainFrame, filteredPostTimeline, true, true)
        }

        holder.bCompetitionInfo?.setOnClickListener {
            activity?.let {
                val infoView = LayoutInflater.from(activity).inflate(R.layout.competition_info, null)
                val infoName = infoView.findViewById<TextView>(R.id.lblCompetitionInfoName)
                val infoDates = infoView.findViewById<TextView>(R.id.lblCompetitionInfoDates)
                val infoDescription = infoView.findViewById<TextView>(R.id.lblCompetitionInfoDescription)

                infoName.text = competition.name
                val dateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
                infoDates.text = "Enrollment uptil: ${dateFormat.format(competition.dateStart)}\n" +
                        "Submission uptil: ${dateFormat.format(competition.dateEnd)}"
                infoDescription.text = competition.description
                AlertDialog.Builder(it).setView(infoView).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CompetitionViewHolder =
            CompetitionViewHolder.fromParent(parent?.context, parent)


    override fun elementForDataSnapshot(dataSnapshot: DataSnapshot): Competition =
            Competition.fromDataSnapshot(dataSnapshot)

}