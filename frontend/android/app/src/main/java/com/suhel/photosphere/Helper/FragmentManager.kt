package com.suhel.photosphere.Helper

import android.app.FragmentManager
import android.support.v4.app.Fragment
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.activity

var currentFragment: Fragment? = null

fun loadFragmentWithFade(resId: Int, frag: Fragment, animate: Boolean, addToBackStack: Boolean = false) {

    try {
        activity?.let {
            val t = it.supportFragmentManager.beginTransaction()
            if (animate) t.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
            if (addToBackStack)
                t.addToBackStack(frag.javaClass.simpleName)
            t.replace(resId, frag)
            t.commit()
            currentFragment = frag
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun pop() {
    activity?.supportFragmentManager?.popBackStack()
}

fun pop(name: String?, inclusive: Boolean) {
    activity?.supportFragmentManager?.popBackStack(name, if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0)
}