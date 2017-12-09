package com.suhel.photosphere.Helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import com.suhel.photosphere.Singleton.REQ_FROM_EXTERNAL_CAMERA
import com.suhel.photosphere.Singleton.REQ_FROM_GALLERY
import com.suhel.photosphere.Singleton.currentFilePath
import com.suhel.photosphere.Singleton.deleteFileAfterUploading
import java.io.File

fun Activity.fireGalleryIntent() {

    startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), "Choose an image"), REQ_FROM_GALLERY)
}

fun Activity.fireExternalCameraIntent() {

    generateFilePath()?.let {
        val externalCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        externalCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(it)))

        if (externalCameraIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(externalCameraIntent, REQ_FROM_EXTERNAL_CAMERA)
        }
    }
}

fun Activity.handleGalleryIntent(intent: Intent?) {

    retrievePathFromIntent(intent)?.let {
        val tempScaledBitmap = decodeSampledBitmapFromFile(it)
        saveBitmap(tempScaledBitmap, generateFilePath())
        tempScaledBitmap?.recycle()


        deleteFileAfterUploading.push(currentFilePath)

        (this as CommonCallback).onAction(Actions.GALLERY_LOAD)

    }

}

fun Activity.handleExternalCameraIntent() {

    currentFilePath?.let {
        val tempScaledBitmap = decodeSampledBitmapFromFile(currentFilePath)
        saveBitmap(tempScaledBitmap, currentFilePath)
        tempScaledBitmap?.recycle()

        (this as CommonCallback).onAction(Actions.EXTERNAL_CAMERA_LOAD)
    }

}

fun Activity.retrievePathFromIntent(data: Intent?): String? {
    val selectedImage = data?.data
    val tempGalleryPath = getPathFromUri(selectedImage)
    if (tempGalleryPath != null)
        return tempGalleryPath
    else
        return selectedImage?.path
}

@SuppressLint("NewApi")
fun Activity.getPathFromUri(uri: Uri?): String? {
    if (uri == null) {
        return null
    }

    val projection = arrayOf(MediaStore.Images.Media.DATA)

    var cursor = getContentResolver().query(uri, projection, null, null, null)

    var path: String? = null

    cursor?.let {
        try {
            val column_index = it.getColumnIndex(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            path = it.getString(column_index).toString()
            it.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return path
}