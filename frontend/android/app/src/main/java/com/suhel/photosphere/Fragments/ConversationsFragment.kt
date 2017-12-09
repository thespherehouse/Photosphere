package com.suhel.photosphere.Fragments

import android.support.v7.widget.LinearLayoutManager
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.fragment_conversations.*

class ConversationsFragment: BaseFragment(R.layout.fragment_conversations) {

    override fun onViewsCreated() {
        fragConversationsList.layoutManager = LinearLayoutManager(context)
    }

}
