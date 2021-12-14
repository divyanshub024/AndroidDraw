package com.divyanshu.androiddraw.jpc

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import java.util.*
import java.io.File
import java.io.FileOutputStream

val imageDir = "${Environment.DIRECTORY_PICTURES}/Android Draw/"

object Utils {

    fun createEmptyBitmap(w: Int, h: Int): Bitmap {
        val conf = Bitmap.Config.ARGB_8888
        return Bitmap.createBitmap(w, h, conf)
    }

    fun saveImage(context: Context, bitmap: Bitmap, fileName: String) {
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

    fun getImagePathList(context: Context): List<Uri> {
        val resultList = ArrayList<Uri>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val externalUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            val projection = arrayOf(MediaStore.Images.Media._ID)

            context.contentResolver.query(
                externalUri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_TAKEN
            )?.use { cursor ->
                val idColumn: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getLong(idColumn)
                    )
                    resultList.add(uri)
                }
            }
        }
        else {
            val path = Environment.getExternalStoragePublicDirectory(imageDir)
            path.mkdirs()
            val imageList = path.listFiles()

            if (imageList.isNullOrEmpty())
                return emptyList()

            for (imagePath in imageList) {
                val uri = imagePath.toUri()
                resultList.add(uri)
            }
        }

        return resultList
    }

}