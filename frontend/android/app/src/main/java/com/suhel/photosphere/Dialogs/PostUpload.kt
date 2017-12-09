package com.suhel.photosphere.Dialogs

import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.suhel.photosphere.DataModels.Types.Derived.Post
import com.suhel.photosphere.Helper.cancelUpload
import com.suhel.photosphere.Singleton.currentFilePath
import com.suhel.photosphere.Helper.deleteFilesInStack
import com.suhel.photosphere.Helper.uploadImage
import com.suhel.photosphere.R
import kotlinx.android.synthetic.main.dialog_upload.*
import java.io.File

class PostUpload : BaseDialog(R.layout.dialog_upload) {

    var state: Int = 0
        set(value) {
            field = value
            when (value) {
                0 -> {
                    bUploadDismiss?.setColorFilter(ContextCompat.getColor(context, R.color.colorPleasantDark))
                    txtUploadImageTitle?.visibility = View.VISIBLE
                    lblUploadPreviewTitle?.visibility = View.GONE
                    progUploadProgress?.visibility = View.GONE

                    txtUploadImageTitle?.setText("", TextView.BufferType.EDITABLE)
                    bUploadAction?.text = "Start Upload"

                    bUploadAction?.setBackgroundResource(R.drawable.green_button_background)

                    dialog.setCancelable(true)
                    dialog.setCanceledOnTouchOutside(true)
                }

                1 -> {
                    bUploadDismiss?.setColorFilter(ContextCompat.getColor(context, R.color.colorInvisibileGrey))
                    txtUploadImageTitle?.visibility = View.GONE
                    lblUploadPreviewTitle?.visibility = View.VISIBLE
                    progUploadProgress?.visibility = View.VISIBLE

                    lblUploadPreviewTitle?.text = txtUploadImageTitle.text.toString().trim()
                    bUploadAction?.text = "Cancel"
                    progUploadProgress?.progress = 0.0f

                    bUploadAction?.setBackgroundResource(R.drawable.red_button_background)

                    dialog.setCancelable(false)
                    dialog.setCanceledOnTouchOutside(false)
                }

                2 -> {
                    bUploadDismiss?.setColorFilter(ContextCompat.getColor(context, R.color.colorPleasantDark))
                    txtUploadImageTitle?.visibility = View.GONE
                    lblUploadPreviewTitle?.visibility = View.VISIBLE
                    progUploadProgress?.visibility = View.VISIBLE

                    bUploadAction?.text = "Done!"
                    progUploadProgress?.progress = 100.0f

                    bUploadAction?.setBackgroundResource(R.drawable.green_button_background)

                    dialog.setCancelable(true)
                    dialog.setCanceledOnTouchOutside(true)
                }

                3 -> {
                    bUploadDismiss?.setColorFilter(ContextCompat.getColor(context, R.color.colorPleasantDark))
                    txtUploadImageTitle?.visibility = View.VISIBLE
                    lblUploadPreviewTitle?.visibility = View.GONE
                    progUploadProgress?.visibility = View.GONE

                    txtUploadImageTitle?.setText("", TextView.BufferType.EDITABLE)
                    bUploadAction?.text = "Try Again"

                    bUploadAction?.setBackgroundResource(R.drawable.red_button_background)

                    dialog.setCancelable(true)
                    dialog.setCanceledOnTouchOutside(true)
                }
            }
        }

    override fun onViewsCreated() {

        currentFilePath?.let {
            imgUploadPreview.setImageURI(Uri.fromFile(File(it)))
        }

        bUploadDismiss?.setOnClickListener {
            this.dismiss()
        }

        bUploadAction?.setOnClickListener {
            if (checkFields())
                uploadAction()
        }

        state = 0
    }

    fun uploadAction() = when (state) {
        0, 3 -> {
            state = 1
            uploadImage(currentFilePath, "images", {
                progress ->

                progUploadProgress.progress = progress

            }, {
                result, firebaseStoragePath ->

                if (result) {

                    deleteFilesInStack()
                    createPostForUpload(txtUploadImageTitle.text.toString().trim(), firebaseStoragePath)

                } else
                    state = 3

            })
        }

        1 -> {
            state = 3
            cancelUpload()
        }

        2 -> {
            this.dismiss()
        }

        else -> {
        }
    }

    private fun createPostForUpload(title: String, firebaseStoragePath: String) {
        FirebaseStorage.getInstance().getReference(firebaseStoragePath).downloadUrl.addOnSuccessListener {
            uri ->

            Post(title, firebaseStoragePath, uri.toString()).insert()
            state = 2
        }
    }

    fun checkFields(): Boolean {
        var flag = true

        val title = txtUploadImageTitle.text.toString().trim()

        if (title == "") {
            txtUploadImageTitle?.setHintTextColor(ContextCompat.getColor(context, R.color.colorSoberRed))
            txtUploadImageTitle?.setText("", TextView.BufferType.EDITABLE)
            txtUploadImageTitle?.hint = "Must have a title"
            flag = false
        } else {
            txtUploadImageTitle?.setHintTextColor(ContextCompat.getColor(context, R.color.colorSubtleHint))
            txtUploadImageTitle?.hint = "Title"
        }

        return flag
    }

}