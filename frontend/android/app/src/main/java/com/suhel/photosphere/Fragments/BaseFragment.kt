package com.suhel.photosphere.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.suhel.photosphere.Helper.CommonCallback
import com.suhel.photosphere.Singleton.auth

abstract class BaseFragment(val resId: Int) : Fragment() {

    protected val callback by lazy { activity as CommonCallback }
    protected val currentUser by lazy { auth.currentUser }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater?.inflate(resId, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewsCreated()
    }

    abstract fun onViewsCreated()

}