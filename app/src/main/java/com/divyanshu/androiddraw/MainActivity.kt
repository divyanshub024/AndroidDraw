package com.divyanshu.androiddraw

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.divyanshu.draw.activity.DrawingActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, DrawingActivity::class.java))

    }
}
