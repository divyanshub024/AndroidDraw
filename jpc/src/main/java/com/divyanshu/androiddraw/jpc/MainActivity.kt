package com.divyanshu.androiddraw.jpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.divyanshu.androiddraw.jpc.ui.theme.AndroidDrawTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDrawTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DrawScreen()
                }
            }
        }
    }
}