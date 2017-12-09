package com.suhel.photosphere.Dialogs

import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.stfalcon.frescoimageviewer.ImageViewer
import com.suhel.photosphere.Singleton.FIELD_USERS_ROOT
import com.suhel.photosphere.Singleton.FIELD_USER_NAME
import com.suhel.photosphere.DataModels.Types.Derived.Post
import com.suhel.photosphere.Fragments.ProfileFragment
import com.suhel.photosphere.Helper.*
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.activity
import com.suhel.photosphere.Singleton.database
import com.suhel.photosphere.Views.Custom.UITextView
import java.text.SimpleDateFormat
import java.util.*

class PostDetailViewDialog {

    companion object {

        private var currentImageViewer: ImageViewer? = null

        private val overlayDetailView by lazy { getOverlayView() }
        private val overlayDetailTitle by lazy { overlayDetailView.findViewById<UITextView>(R.id.lblDetailTitle) as UITextView }
        private val overlayDetailDate by lazy { overlayDetailView.findViewById<UITextView>(R.id.lblDetailDate) as UITextView }
        private val overlayDetailOwnerName by lazy { overlayDetailView.findViewById<UITextView>(R.id.lblDetailOwnerName) as UITextView }
        private val overlayDetailEdit by lazy { overlayDetailView.findViewById<ImageButton>(R.id.bDetailEdit) as ImageButton }
        private val overlayDetailProfile by lazy { overlayDetailView.findViewById<ImageButton>(R.id.bDetailProfile) as ImageButton }
        private val overlayDetailLike by lazy { overlayDetailView.findViewById<ImageButton>(R.id.bDetailLike) as ImageButton }
        private val overlayDetailShare by lazy { overlayDetailView.findViewById<ImageButton>(R.id.bDetailShare) as ImageButton }
        private val overlayDetailDelete by lazy { overlayDetailView.findViewById<ImageButton>(R.id.bDetailDelete) as ImageButton }

        private fun buildDetailView(data: List<Post>, startPosition: Int): ImageViewer.Builder<*> = ImageViewer.Builder<Post>(activity, data)
                .setFormatter(Post::fileURL)
                .setStartPosition(startPosition)
                .setImageChangeListener {
                    bindOverlayViews(data, it)
                }
                .setOverlayView(overlayDetailView)
                .setOnDismissListener {
                    (overlayDetailView.parent as ViewGroup).removeView(overlayDetailView)
                    currentImageViewer = null
                }

        private fun getOverlayView(): View = LayoutInflater.from(activity)
                .inflate(R.layout.detail_overlay, null)

        private fun bindOverlayViews(data: List<Post>, position: Int) {

            overlayDetailEdit.setupSpringAnimations()
            overlayDetailProfile.setupSpringAnimations()
            overlayDetailLike.setupSpringAnimations()
            overlayDetailShare.setupSpringAnimations()
            overlayDetailDelete.setupSpringAnimations()

            // Bind view states
            if (data[position].isMyPost()) {
                overlayDetailEdit.isEnabled = true
                overlayDetailDelete.isEnabled = true

                overlayDetailEdit.setColorFilter(ContextCompat.getColor(activity, R.color.colorBrightLight))
                overlayDetailDelete.setColorFilter(ContextCompat.getColor(activity, R.color.colorBrightLight))
            } else {
                overlayDetailEdit.isEnabled = false
                overlayDetailDelete.isEnabled = false

                overlayDetailEdit.setColorFilter(ContextCompat.getColor(activity, R.color.colorPleasantDark))
                overlayDetailDelete.setColorFilter(ContextCompat.getColor(activity, R.color.colorPleasantDark))
            }

            // Bind values
            overlayDetailTitle.text = data[position].title
            overlayDetailLike.setImageResource(if (data[position].isLikedByMe()) R.drawable.ic_like_filled else R.drawable.ic_like_outline)
            database.getReference(FIELD_USERS_ROOT).child(data[position].ownerId).child(FIELD_USER_NAME)
                    .addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onDataChange(snapshot: DataSnapshot?) {
                            snapshot?.let {
                                overlayDetailOwnerName.text = it.getValue(String::class.java) ?: "Anonymous"
                            }
                        }

                    })
            overlayDetailOwnerName.text = "Loading"
            val dateFormatter = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())
            overlayDetailDate.text = dateFormatter.format(data[position].date).toUpperCase()

            // Bind events
            overlayDetailEdit.setOnClickListener {
                val dialogView = LayoutInflater.from(activity).inflate(R.layout.detail_post_edit_dialog, null)
                val field = dialogView.findViewById<EditText>(R.id.txtDetailEditDialogTitle)
                field.setText(data[position].title, TextView.BufferType.EDITABLE)

                AlertDialog.Builder(activity!!)
                        .setView(dialogView)
                        .setPositiveButton("Done", {
                            _, _ ->

                            val title = field.text.toString().trim()
                            if (title != "")
                                data[position].updateTitle(title)
                            else
                                Toast.makeText(activity, "Title cannot be null", Toast.LENGTH_SHORT).show()
                        })
                        .setNegativeButton("Cancel", null)
                        .setCancelable(false)
                        .show()
            }
            overlayDetailProfile.setOnClickListener {
                SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Visiting profile ${data[position].ownerId}")
                PostDetailViewDialog.dismiss()
                loadFragmentWithFade(R.id.mainFrame, ProfileFragment(data[position].ownerId), true, true)
            }
            overlayDetailLike.setOnClickListener {
                if (data[position].isLikedByMe()) {
                    data[position].removeMyLike()
                    overlayDetailLike.setImageResource(R.drawable.ic_like_outline)
                } else {
                    data[position].addMyLike()
                    overlayDetailLike.setImageResource(R.drawable.ic_like_filled)
                }
            }
            overlayDetailShare.setOnClickListener {
                getBitmapFromFresco(data[position].fileURL, {
                    bitmap ->

                    SimpleAnalytics.logEvent(SimpleAnalytics.EventType.ACTION, "Posting ${data[position].id} to facebook")
                    PostDetailViewDialog.dismiss()
                    val photo = SharePhoto.Builder()
                            .setBitmap(bitmap)
                            .setCaption("Photosphere BETA")
                            .build()
                    val content = SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build()
                    val shareDialog = ShareDialog(activity)
                    shareDialog.show(content, ShareDialog.Mode.AUTOMATIC)
                })
            }
            overlayDetailDelete.setOnClickListener {
                AlertDialog.Builder(activity!!)
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("I'm sure", {
                            _, _ ->

                            data[position].remove()
                        })
                        .setNegativeButton("Nope", null)
                        .setCancelable(false)
                        .show()
            }
        }

        fun show(data: List<Post>, startPosition: Int) {
            if (currentImageViewer != null)
                return
            currentImageViewer = buildDetailView(data, startPosition).build()
            currentImageViewer?.show()
        }

        fun dismiss() {
            if (currentImageViewer == null)
                return

            currentImageViewer?.onDismiss()
            currentImageViewer = null
        }

    }

}
