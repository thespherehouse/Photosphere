package com.suhel.photosphere.Helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.datasource.DataSubscriber
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.suhel.photosphere.Singleton.activity
//import android.support.v8.renderscript.Allocation
//import android.support.v8.renderscript.Element
//import android.support.v8.renderscript.RenderScript
//import android.support.v8.renderscript.ScriptIntrinsicBlur
import java.io.ByteArrayOutputStream


val DEFAULT_MAX_BITMAP_SIZE = 512

fun saveBitmap(bitmap: Bitmap?, filePath: String?) {
    if (filePath != null && bitmap != null) {
        val blob = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob)
        saveByteArray(blob.toByteArray(), filePath)
    }

}

fun Bitmap.resizeBitmap(maxSize: Int = DEFAULT_MAX_BITMAP_SIZE): Bitmap? {

    var scale = 1

    if (Math.max(width, height) > maxSize)
        while ((width / scale > maxSize) || (height / scale > maxSize))
            scale++

    return Bitmap.createScaledBitmap(this, this.width / scale, this.height / scale, false)

}

fun Bitmap.rotateBitmap(rotationAngle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(rotationAngle)
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, false)
}

private fun calculateInSampleSize(options: BitmapFactory.Options, maxSize: Int): Int {
    val height = options.outHeight
    val width = options.outWidth

    var inSampleSize = 1

    if (Math.max(width, height) > maxSize)
        while ((width / inSampleSize > maxSize) || (height / inSampleSize > maxSize))
            inSampleSize++

    return inSampleSize
}

fun decodeSampledBitmapFromFile(filePath: String?, maxSize: Int = DEFAULT_MAX_BITMAP_SIZE): Bitmap? {

    filePath?.let {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(it, options)
        options.inSampleSize = calculateInSampleSize(options, maxSize)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(it, options)
    }

    return null
}

fun decodeSampledBitmapFromByteArray(blob: ByteArray?, maxSize: Int = DEFAULT_MAX_BITMAP_SIZE): Bitmap? {
    blob?.let {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(it, 0, it.size, options)
        options.inSampleSize = calculateInSampleSize(options, maxSize)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(it, 0, it.size, options)

    }

    return null
}

//fun blur(context: Context, image: Bitmap?): Bitmap? {
//
//    image?.let {
//
//        val width = Math.round(it.width * 0.4f)
//        val height = Math.round(it.height * 0.4f)
//
//        val inputBitmap = Bitmap.createScaledBitmap(it, width, height, true)
//        val outputBitmap = Bitmap.createBitmap(inputBitmap)
//
//        val rs = RenderScript.create(context, RenderScript.ContextType.DEBUG)
//        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
//        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
//        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
//        theIntrinsic.setRadius(25.0f)
//        theIntrinsic.setInput(tmpIn)
//        theIntrinsic.forEach(tmpOut)
//        tmpOut.copyTo(outputBitmap)
//
//        return outputBitmap
//    }
//
//    return null
//}

fun getBitmapFromFresco(url: String, callback: ((Bitmap?) -> Unit)?) {
    val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build()
    val imagePipeline = Fresco.getImagePipeline()
    val dataSource = imagePipeline.fetchDecodedImage(imageRequest, null)
    dataSource.subscribe(object : BaseBitmapDataSubscriber() {

        override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
        }

        override fun onNewResultImpl(bitmap: Bitmap?) {
            callback?.invoke(bitmap)
        }

    }, CallerThreadExecutor.getInstance())

}