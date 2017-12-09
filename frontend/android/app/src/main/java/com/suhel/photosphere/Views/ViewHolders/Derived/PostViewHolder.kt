package com.suhel.photosphere.Views.ViewHolders.Derived

import android.content.Context
import android.graphics.drawable.Animatable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.suhel.photosphere.Singleton.FIELD_USERS_ROOT
import com.suhel.photosphere.Singleton.FIELD_USER_NAME
import com.suhel.photosphere.DataModels.Types.Derived.Post
import com.suhel.photosphere.Helper.setupSpringAnimations
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.auth
import com.suhel.photosphere.Singleton.database
import com.suhel.photosphere.Views.Custom.CircleProgressDrawable
import com.suhel.photosphere.Views.ViewHolders.BaseViewHolder

class PostViewHolder private constructor(val context: Context?, itemView: View) : BaseViewHolder<Post>(itemView) {

    var imgPostImage: SimpleDraweeView? = null
    var lblPostTitle: TextView? = null
    var lblPostOwner: TextView? = null
    var lblPostLikes: TextView? = null
    var bPostLike: ImageView? = null

    var likeState = false
        set(value) {
            field = value

            when (value) {
                true -> bPostLike?.setImageResource(R.drawable.ic_like_filled)
                false -> bPostLike?.setImageResource(R.drawable.ic_like_outline)
            }
        }

    var likes = 0
        set(value) {
            field = value
            lblPostLikes?.text = "$value"
        }

    override fun bindViews(itemView: View) {
        this.imgPostImage = itemView.findViewById(R.id.imgPostImage) as SimpleDraweeView
        this.lblPostTitle = itemView.findViewById(R.id.lblPostTitle) as TextView
        this.lblPostOwner = itemView.findViewById(R.id.lblPostOwner) as TextView
        this.lblPostLikes = itemView.findViewById(R.id.lblPostLikes) as TextView
        this.bPostLike = itemView.findViewById(R.id.bPostLike) as ImageView

    }

    override fun initViews(itemView: View) {
        bPostLike?.setupSpringAnimations()
        this.imgPostImage?.hierarchy?.setProgressBarImage(CircleProgressDrawable())
    }

    override fun bindData(data: Post) {
        loadImageIntoDrawee(data)

        if (data.fileURL == "" && data.filePath != "") {

            FirebaseStorage.getInstance().getReference(data.filePath).downloadUrl.addOnSuccessListener {
                uri ->

                data.updateFileURL(uri.toString())
            }
        }

        this.lblPostTitle?.text = data.title
        database.getReference(FIELD_USERS_ROOT).child(data.ownerId).child(FIELD_USER_NAME)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        this@PostViewHolder.lblPostOwner?.text = snapshot?.getValue(String::class.java) ?: "Anonymous"
                    }

                })
        this.likes = data.likedBy.count()
        this.likeState = data.likedBy.contains(auth.currentUser?.uid ?: "")
    }

    fun loadImageIntoDrawee(data: Post) {
        val uri = Uri.parse(data.fileURL)

        val imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setImageRequest(imageRequest)
                .setOldController(this.imgPostImage?.controller)
                .build()
        this.imgPostImage?.controller = controller
    }

    //For maintaining aspect ratio of the Drawee in Fresco
    val controllerListener = object : BaseControllerListener<ImageInfo>() {

        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
            imageInfo?.let {
                imgPostImage?.aspectRatio = it.width.toFloat() / it.height.toFloat()
            }
        }
    }

    companion object {

        fun fromParent(context: Context?, parent: ViewGroup?): PostViewHolder {
            val itemView = LayoutInflater.from(context).inflate(R.layout.card_post_alt, parent, false)
            return PostViewHolder(context, itemView)
        }

    }
}