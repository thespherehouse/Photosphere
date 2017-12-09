package com.suhel.photosphere.Dialogs

import android.animation.LayoutTransition
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.suhel.photosphere.Helper.CommonCallback
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.auth

abstract class BaseDialog(val resId: Int) : DialogFragment() {

    protected val callback by lazy { activity as CommonCallback }
    protected val currentUser by lazy { auth.currentUser }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(resId, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (view as? ViewGroup)?.layoutTransition?.enableTransitionType(LayoutTransition.CHANGING)

        onViewsCreated()
    }

    abstract fun onViewsCreated()

}