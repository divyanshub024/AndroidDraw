package com.divyanshu.androiddraw.jpc

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object Utils {

    fun createEmptyBitmap(w: Int, h: Int): Bitmap {
        val conf = Bitmap.Config.ARGB_8888
        return Bitmap.createBitmap(w, h, conf)
    }

    fun saveImage(context: Context, bitmap: Bitmap, fileName: String) {
        val imageDir = "${Environment.DIRECTORY_PICTURES}/Android Draw/"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, imageDir)
            }

            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                resolver.openOutputStream(it).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream)
                    outputStream?.flush()
                    outputStream?.close()
                }
            }
        }
        else {
            val path = Environment.getExternalStoragePublicDirectory(imageDir)
            Log.e("path",path.toString())
            val file = File(path, "$fileName.png")
            path.mkdirs()
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream)
            outputStream.flush()
            outputStream.close()
        }
    }

}