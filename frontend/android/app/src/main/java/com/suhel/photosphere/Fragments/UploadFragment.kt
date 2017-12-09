package com.suhel.photosphere.Fragments

import android.graphics.BitmapFactory
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.suhel.photosphere.DataModels.Types.Derived.Post
import com.suhel.photosphere.Helper.Actions
import com.suhel.photosphere.Helper.cancelUpload
import com.suhel.photosphere.Helper.deleteFilesInStack
import com.suhel.photosphere.Helper.uploadImage
import com.suhel.photosphere.R
import com.suhel.photosphere.Singleton.currentFilePath
import kotlinx.android.synthetic.main.dialog_upload.*

class UploadFragment : BaseFragment(R.layout.dialog_upload) {

    override fun onViewsCreated() {

        bUploadAction?.setOnClickListener {

            when (bUploadAction.text) {
                "upload", "retry" -> {

                    val title = txtUploadImageTitle.text.toString().trim()

                    if (title == "") {
                        txtUploadImageTitle.setHintTextColor(ResourcesCompat.getColor(context.resources, R.color.theRedShade, null))
                        txtUploadImageTitle.setText("", TextView.BufferType.EDITABLE)
                        txtUploadImageTitle.hint = "Title Mandatory"
                    } else {
                        txtUploadImageTitle.setHintTextColor(ResourcesCompat.getColor(context.resources, R.color.theTranslucentDarkShade, null))
                        txtUploadImageTitle.hint = "Add Title"

                        startUploading(title)
                    }

                }

                "cancel" -> {
                    cancelUpload()

                    setStateCancelled()
                }

                "back to timeline" -> {
                    callback.onAction(Actions.ACTION_NAV_TIMELINE)
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (!callback.isModalTaskOngoing()) {

            currentFilePath?.let {
                imgUploadPreview.setImageBitmap(BitmapFactory.decodeFile(it))
            }

        }
    }

    override fun onPause() {
        super.onPause()
        if (!callback.isModalTaskOngoing())
            deleteFilesInStack()
    }

    private fun setInterfaceVisibility(visibility: Boolean) {
        when (visibility) {

            false -> {

                txtUploadImageTitle?.visibility = View.VISIBLE
            }

            true -> {

                txtUploadImageTitle?.visibility = View.GONE
            }

        }
    }

    private fun startUploading(title: String) {
        callback.setModalTaskOngoing(true)

        setStateOngoing()

        uploadImage(currentFilePath, "images", {
            progress ->

            progUploadProgress?.progress = progress

        }, {
            result, firebaseStoragePath ->

            deleteFilesInStack()
            createPostForUpload(title, result, firebaseStoragePath)
        })
    }

    private fun createPostForUpload(title: String, result: Boolean, firebaseStoragePath: String) {
        FirebaseStorage.getInstance().getReference(firebaseStoragePath).downloadUrl.addOnSuccessListener {
            uri ->

            Post(title, firebaseStoragePath, uri.toString()).insert()
            callback.setModalTaskOngoing(false)
            if (result)
                setStateSuccess()
            else
                setStateFailure()
        }
    }

    private fun resetEverything() {
        setInterfaceVisibility(false)
        bUploadAction?.text = "upload"
        bUploadAction?.setBackgroundResource(R.color.theGreenShade)
        txtUploadImageTitle?.setText("", TextView.BufferType.EDITABLE)
        progUploadProgress?.progress = 0f
    }

    private fun setStateCancelled() {
        resetEverything()
        bUploadAction?.text = "retry"
    }

    private fun setStateSuccess() {
        setInterfaceVisibility(true)
        bUploadAction?.text = "back to timeline"
        bUploadAction?.setBackgroundResource(R.color.theGreenShade)
    }

    private fun setStateFailure() {
        setInterfaceVisibility(true)
        bUploadAction?.text = "retry"
        progUploadProgress?.progress = 0f
    }

    private fun setStateOngoing() {
        setInterfaceVisibility(true)

        bUploadAction?.text = "cancel"
        bUploadAction?.setBackgroundResource(R.color.theRedShade)
    }
}