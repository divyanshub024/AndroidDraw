package com.divyanshu.androiddraw.jpc

import android.util.Xml
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import org.xmlpull.v1.XmlPullParser
import com.divyanshu.draw.widget.DrawView
import java.util.*

@ExperimentalAnimationApi
@Composable
fun DrawScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // States in which a particular tool is selected
    val showDrawTools = remember { mutableStateOf(false) } // Needed for ColorPalette, StrokeWidth, and Opacity
    val isEraserSelected = remember { mutableStateOf(false) }
    val isColorPaletteSelected = remember { mutableStateOf(false) }
    val isStrokeWidthSelected = remember { mutableStateOf(false) }
    val isOpacitySelected = remember { mutableStateOf(false) }
    val isUndoSelected = remember { mutableStateOf(false) }
    val isRedoSelected = remember { mutableStateOf(false) }

    // Currently-selected color, stroke width, and opacity
    val pathColor = remember { mutableStateOf(Color.Black) }
    val strokeWidth = remember { mutableStateOf(5f) }
    val opacity = remember { mutableStateOf(100f) }

    // The color of the eraser. It has to be the same as the background color
    val backgroundColor = MaterialTheme.colors.background

    // For saving
    val showSaveDialog = remember { mutableStateOf(false) }
    val resultBitmap = remember { mutableStateOf(Utils.createEmptyBitmap(1, 1)) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Close button and Save button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.Default.Close, tint = Color.White, contentDescription = "Close")
            }
            IconButton(
                modifier = Modifier.size(42.dp),
                onClick = {
                    showSaveDialog.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.Check, tint = Color.White, contentDescription = "Save")
            }
        }
        Divider()
        // Get the DrawView from xml
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = {
                val parser: XmlPullParser = it.resources.getXml(R.xml.draw_view)
                try {
                    parser.next()
                    parser.nextTag()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val attrs = Xml.asAttributeSet(parser)
                DrawView(it, attrs)
            }
        ) { drawView ->
            drawView.setColor(if (isEraserSelected.value) backgroundColor.toArgb() else pathColor.value.toArgb())
            drawView.setStrokeWidth(strokeWidth.value)
            drawView.setAlpha(if (isEraserSelected.value) 100 else opacity.value.toInt())

            if (isUndoSelected.value) {
                drawView.undo()
                isUndoSelected.value = false
            }
            if (isRedoSelected.value) {
                drawView.redo()
                isRedoSelected.value = false
            }
            if (showSaveDialog.value) {
                resultBitmap.value = drawView.getBitmap()
            }
        }
        // Toolbar at the bottom
        Divider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = {
                    showDrawTools.value = false
                    isEraserSelected.value = !isEraserSelected.value
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_eraser_black_24dp),
                    tint = if(isEraserSelected.value) Color.Black else Color.Gray,
                    contentDescription = "Eraser"
                )
            }
            IconButton(
                onClick = {
                    showDrawTools.value = true
                    isStrokeWidthSelected.value = true
                    isColorPaletteSelected.value = false
                    isOpacitySelected.value = false
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_adjust_black_24dp),
                    tint = Color.Gray,
                    contentDescription = "Stroke Width"
                )
            }
            IconButton(
                onClick = {
                    showDrawTools.value = true
                    isStrokeWidthSelected.value = false
                    isColorPaletteSelected.value = true
                    isOpacitySelected.value = false
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_color_lens_black_24dp),
                    tint = Color.Gray,
                    contentDescription = "Color"
                )
            }
            IconButton(
                onClick = {
                    showDrawTools.value = true
                    isStrokeWidthSelected.value = false
                    isColorPaletteSelected.value = false
                    isOpacitySelected.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_opacity_black_24dp),
                    tint = Color.Gray,
                    contentDescription = "Opacity"
                )
            }
            IconButton(
                onClick = {
                    showDrawTools.value = false
                    isUndoSelected.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_undo_black_24dp),
                    tint = Color.Gray,
                    contentDescription = "Undo"
                )
            }
            IconButton(
                onClick = {
                    showDrawTools.value = false
                    isRedoSelected.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_redo_black_24dp),
                    tint = Color.Gray,
                    contentDescription = "Redo"
                )
            }
        }
        // Box that comes up animated from the bottom. Only for ColorPalette, StrokeWidth, and Opacity
        AnimatedVisibility(visible = showDrawTools.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(15.dp, 2.dp)
            ) {
                when {
                    isColorPaletteSelected.value -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Black) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black)
                                    .clickable { pathColor.value = Color.Black }
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Red) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red)
                                    .clickable { pathColor.value = Color.Red }
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Yellow) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Yellow)
                                    .clickable { pathColor.value = Color.Yellow }
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Green) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Green)
                                    .clickable { pathColor.value = Color.Green }
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Blue) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Blue)
                                    .clickable { pathColor.value = Color.Blue }
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Gray) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                                    .clickable { pathColor.value = Color.Gray }
                            )
                            Box(
                                modifier = Modifier
                                    .size(if (pathColor.value == Color.Magenta) 38.dp else 32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Magenta)
                                    .clickable { pathColor.value = Color.Magenta }
                            )
                        }
                    }
                    isStrokeWidthSelected.value -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Slider(
                                modifier = Modifier
                                    .padding(5.dp, 0.dp)
                                    .weight(1f),
                                value = strokeWidth.value,
                                onValueChange = { strokeWidth.value = it },
                                valueRange = 0f..100f,
                                steps = 100,
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colors.secondary,
                                    activeTrackColor = MaterialTheme.colors.secondary
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp * strokeWidth.value / 100f)
                                        .clip(CircleShape)
                                        .background(pathColor.value)
                                )
                            }
                        }
                    }
                    isOpacitySelected.value -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Slider(
                                modifier = Modifier
                                    .padding(5.dp, 0.dp)
                                    .weight(1f),
                                value = opacity.value,
                                onValueChange = { opacity.value = it },
                                valueRange = 0f..100f,
                                steps = 100,
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colors.secondary,
                                    activeTrackColor = MaterialTheme.colors.secondary
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(
                                        // alpha has to be within the range: 0f..1f
                                        pathColor.value.copy(alpha = opacity.value / 100f)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSaveDialog.value) {
        val fileName = remember { mutableStateOf(UUID.randomUUID().toString()) }
        AlertDialog(
            onDismissRequest = { showSaveDialog.value = false },
            title = {
                Text(
                    text = "Save Drawing",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                OutlinedTextField(
                    value = fileName.value,
                    maxLines = 1,
                    onValueChange = { fileName.value = it }
                )
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showSaveDialog.value = false }
                ) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        Utils.saveImage(context, resultBitmap.value, fileName.value)
                        navController.navigateUp()
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}