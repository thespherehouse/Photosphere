package com.suhel.photosphere.Views.ViewHolders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.suhel.photosphere.DataModels.Types.BaseType

abstract class BaseViewHolder<in T : BaseType>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        this.bindViews(itemView)
        this.initViews(itemView)
    }

    protected abstract fun bindViews(itemView: View)

    protected abstract fun initViews(itemView: View)

    abstract fun bindData(data: T)

}