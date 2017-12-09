package com.suhel.photosphere.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suhel.photosphere.Helper.Actions
import com.suhel.photosphere.Helper.CommonCallback
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    override fun onViewsCreated() {
        bLoginGoogle?.setOnClickListener {
            callback.onAction(Actions.ACTION_LOGIN_GOOGLE)
        }

        bLoginFacebook?.setOnClickListener {
            callback.onAction(Actions.ACTION_LOGIN_FACEBOOK)
        }
    }

    override fun onResume() {
        super.onResume()

        deckLogin?.visibility = View.GONE

        view?.postDelayed({
            deckLogin?.visibility = View.VISIBLE
        }, 1000)

        callback.showAppBar(false)
    }

}