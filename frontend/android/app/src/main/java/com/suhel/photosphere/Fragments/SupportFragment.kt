package com.suhel.photosphere.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.suhel.photosphere.Helper.SimpleAnalytics
import com.suhel.photosphere.Singleton.support
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.fragment_support.*

class SupportFragment : BaseFragment(R.layout.fragment_support) {

    override fun onViewsCreated() {
        bSupportDonate.setOnClickListener {
            SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Donate clicked")
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://www.paypal.me/suhelchakraborty"
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        bSupportSubmitFeedback.setOnClickListener {
            val feedback = txtSupportFeedback.text.toString().trim()
            if(feedback == "") {
                Toast.makeText(context, "Please write something", Toast.LENGTH_SHORT).show()
            }
            SimpleAnalytics.logFeedback(feedback, {
                Toast.makeText(context, "Thanks for your feedback", Toast.LENGTH_SHORT).show()
                txtSupportFeedback.setText("", TextView.BufferType.EDITABLE)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        tvSupportMessage.text = support.message
        callback.setAppFullscreen(false)
        callback.showAppBar(true)
        callback.setTitle("support")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }


}
