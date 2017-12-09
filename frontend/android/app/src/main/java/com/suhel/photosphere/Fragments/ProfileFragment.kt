package com.suhel.photosphere.Fragments

import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.RoundingParams
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.google.firebase.database.*
import com.suhel.photosphere.DataModels.Adapters.Derived.PostsAdapter
import com.suhel.photosphere.Singleton.FIELD_POSTS_ROOT
import com.suhel.photosphere.Singleton.FIELD_POST_OWNER_ID
import com.suhel.photosphere.Singleton.FIELD_USERS_ROOT
import com.suhel.photosphere.DataModels.Types.Derived.User
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment(val userId: String) : BaseFragment(R.layout.fragment_profile) {

    private val postsAdapter: PostsAdapter<Query> by lazy {
        PostsAdapter(FirebaseDatabase.getInstance()
                .reference.child(FIELD_POSTS_ROOT).orderByChild(FIELD_POST_OWNER_ID)
                .equalTo(userId))
    }

    override fun onViewsCreated() {
        FirebaseDatabase.getInstance().reference.child(FIELD_USERS_ROOT)
                .child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(child: DataSnapshot?) {

                        child?.let {
                            val user = User.fromDataSnapshot(it)

                            lblProfileName?.text = user.name

                            val roundingParams = RoundingParams.asCircle()
                            roundingParams.borderWidth = 10f
                            roundingParams.borderColor = Color.WHITE
                            val uri = Uri.parse(user.profilePicUrl)
                            imgProfilePicture?.hierarchy?.roundingParams = roundingParams
                            imgProfilePicture?.hierarchy?.setPlaceholderImage(R.drawable.profile_pic_placeholder)
                            imgProfilePicture?.setImageURI(uri, null)
                        }

                    }

                    override fun onCancelled(p0: DatabaseError?) {
                    }

                })

        timelineProfile?.layoutManager = LinearLayoutManager(context)
        timelineProfile?.adapter = postsAdapter

        timelineProfile?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 10) {
                    callback.setTitleVisibility(false)
                } else if (dy < -10) {
                    callback.setTitleVisibility(true)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        callback.setTitle("profile")
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        postsAdapter.attachDataSource()
    }

    override fun onStop() {
        super.onStop()
        postsAdapter.detachDataSource()
    }

}