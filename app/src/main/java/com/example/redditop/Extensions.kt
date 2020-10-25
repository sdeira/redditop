package com.example.redditop

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

const val QUALITY = 100

fun String.saveImage(context: Context, name: String) {
    Glide.with(context)
        .asBitmap()
        .load(this)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                // Nothing to do here
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                saveImage(context, resource, name)
            }
        })
}

private fun saveImage(context: Context, bitmap: Bitmap, name: String) {
    try {
        val fos: OutputStream?
        val directoryName = "Redditop"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/$directoryName")
            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        } else {
            val imagesDir = Environment.getExternalStorageDirectory().toString() + File.separator + directoryName
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, "$name.png")
            fos = FileOutputStream(image)
        }
        fos?.let {
            val saved = bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, fos)
            if (saved) {
                Toast.makeText(context, context.resources.getString(R.string.image_downloaded),
                    Toast.LENGTH_LONG).show()
            }
            it.flush()
            it.close()
        }
    } catch (exception: IOException) {
        Log.i("Fail to save Image", exception.toString())
        Toast.makeText(context, context.resources.getString(R.string.image_downloaded_fails), Toast.LENGTH_LONG).show()
    }
}
