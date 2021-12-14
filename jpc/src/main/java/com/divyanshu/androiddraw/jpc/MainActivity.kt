package com.divyanshu.androiddraw.jpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.divyanshu.androiddraw.jpc.ui.theme.AndroidDrawTheme

@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            this.window.statusBarColor = ContextCompat.getColor(this,R.color.black)

            AndroidDrawTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable(route = "list") {
                            ListScreen(navController = navController)
                        }
                        composable(route = "draw") {
                            DrawScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}