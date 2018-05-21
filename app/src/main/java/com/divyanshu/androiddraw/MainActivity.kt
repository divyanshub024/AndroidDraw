package com.divyanshu.androiddraw

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.divyanshu.draw.activity.DrawingActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

private const val REQUEST_CODE_DRAW = 101
private const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102
class MainActivity : AppCompatActivity() {

    lateinit var adapter: DrawAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
        }else{
            adapter = DrawAdapter(this,getFilesPath())
            recycler_view.adapter = adapter
        }
        fab_add_draw.setOnClickListener {
            val intent = Intent(this, DrawingActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_DRAW)
        }
    }

    private fun getFilesPath(): ArrayList<String>{
        val resultList = ArrayList<String>()
        val imageDir = "${Environment.DIRECTORY_PICTURES}/Android Draw/"
        val path = Environment.getExternalStoragePublicDirectory(imageDir)
        val imageList = path.listFiles()
        for (imagePath in imageList){
            resultList.add(imagePath.absolutePath)
        }
        return resultList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            when(requestCode){
                REQUEST_CODE_DRAW -> {
                    val result= data.getByteArrayExtra("bitmap")
                    val bitmap = BitmapFactory.decodeByteArray(result, 0, result.size)
                    saveImage(bitmap)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    adapter = DrawAdapter(this,getFilesPath())
                    recycler_view.adapter = adapter
                }else{
                    finish()
                }
                return
            }
            else -> {}
        }
    }

    private fun saveImage(bitmap: Bitmap) {
        val imageDir = "${Environment.DIRECTORY_PICTURES}/Android Draw/"
        val path = Environment.getExternalStoragePublicDirectory(imageDir)
        val file = File(path, UUID.randomUUID().toString()+".png")
        path.mkdirs()
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        outputStream.flush()
        outputStream.close()
        updateRecyclerView(Uri.fromFile(file))
    }

    private fun updateRecyclerView(uri: Uri) {
        adapter.addItem(uri)
    }
}
