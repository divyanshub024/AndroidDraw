package com.divyanshu.androiddraw.jpc

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@ExperimentalFoundationApi
@Composable
fun ListScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val gridListState = rememberLazyListState()

    val imagePathList = remember { mutableStateOf(Utils.getImagePathList(context)) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("ListScreen","PERMISSION GRANTED")
        } else {
            Log.d("ListScreen","PERMISSION DENIED")
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                Log.d("ListScreen","Has WRITE_EXTERNAL_STORAGE permission")
            }
            else -> {
                // Asking for permission
                launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        imagePathList.value = Utils.getImagePathList(context)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("draw")
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.Black)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(10.dp, 0.dp),
                    text = "Android Draw",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                cells = GridCells.Fixed(count = 2),
                state = gridListState,
                contentPadding = PaddingValues(
                    start = 6.dp,
                    top = 8.dp,
                    end = 6.dp,
                    bottom = 8.dp
                ),
                content = {
                    items(imagePathList.value.size) { index ->
                        val imagePath = imagePathList.value[index]

                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            val source = ImageDecoder.createSource(context.contentResolver, imagePath)
                            ImageDecoder.decodeBitmap(source)
                        }
                        else {
                            MediaStore.Images.Media.getBitmap(context.contentResolver, imagePath)
                        }

                        bitmap?.let {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(1.dp),
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(width = 1.dp, color = Color.Gray)
                            ) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            // todo: do something upon click
                                        },
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "Image"
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}