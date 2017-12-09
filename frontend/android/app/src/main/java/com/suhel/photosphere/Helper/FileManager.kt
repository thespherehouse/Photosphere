package com.suhel.photosphere.Helper

import android.os.Environment
import com.suhel.photosphere.Singleton.APP_NAME
import com.suhel.photosphere.Singleton.currentFilePath
import com.suhel.photosphere.Singleton.deleteFileAfterUploading
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun saveByteArray(data: ByteArray?, filePath: String?) {
    if (filePath != null && data != null) {
        val fileOutputStream = FileOutputStream(File(filePath), false)

        fileOutputStream.write(data)
        fileOutputStream.close()
    }
}

fun generateFilePath(): String? {
    val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_NAME)

    if (!dir.exists())
        if (!dir.mkdirs()) {
            currentFilePath = null
            return null
        }

    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    currentFilePath = dir.path + File.separator + "IMG_" + timestamp + ".jpg"
    return currentFilePath
}

fun deleteFilesInStack() {
    while (!deleteFileAfterUploading.empty()) {
        File(deleteFileAfterUploading.pop()).delete()
    }
}