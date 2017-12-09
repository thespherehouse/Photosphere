package com.suhel.photosphere.Views.ViewHolders.Derived

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.suhel.photosphere.DataModels.Types.Derived.Competition
import com.suhel.photosphere.R
import com.suhel.photosphere.Views.ViewHolders.BaseViewHolder

class CompetitionViewHolder private constructor(val context: Context?, itemView: View) : BaseViewHolder<Competition>(itemView) {

    var imgCompetitionCover: ImageView? = null
    var lblCompetitionName: TextView? = null
    var lblCompetitionStatus: TextView? = null
    var bCompetitionInfo: ImageButton? = null

    override fun bindViews(itemView: View) {
        imgCompetitionCover = itemView.findViewById(R.id.imgCompetitionCover) as ImageView
        lblCompetitionName = itemView.findViewById(R.id.lblCompetitionName) as TextView
        lblCompetitionStatus = itemView.findViewById(R.id.lblCompetitionStatus) as TextView
        bCompetitionInfo = itemView.findViewById(R.id.bCompetitionInfo) as ImageButton
    }

    override fun initViews(itemView: View) {

    }

    override fun bindData(data: Competition) {

        if (data.isCoverPresent())
//            getBitmapFromCacheByFirebasePath(context, data.coverPath, {
//                bitmap, _ ->
//
//                imgCompetitionCover?.setImageBitmap(bitmap)
//            })

            lblCompetitionName?.text = data.name

        if (data.isOngoing())
            lblCompetitionStatus?.text = "ongoing"
        else if (data.isEnrollable())
            lblCompetitionStatus?.text = "enroll"
        else
            lblCompetitionStatus?.text = "ended"

    }

    companion object {

        fun fromParent(context: Context?, parent: ViewGroup?): CompetitionViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.card_competition, parent, false)
            return CompetitionViewHolder(context, itemView)
        }

    }

}