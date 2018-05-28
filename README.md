# Android Draw [![](https://jitpack.io/v/divyanshub024/AndroidDraw.svg)](https://jitpack.io/#divyanshub024/AndroidDraw)

A drawing view for your android application.

<img src="https://github.com/divyanshub024/AndroidDraw/blob/master/Art/icon.png" width="120">

<a href='https://play.google.com/store/apps/details?id=com.divyanshu.androiddraw&rdid=com.divyanshu.androiddraw&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width = "320"/></a>							

<img src="https://github.com/divyanshub024/AndroidDraw/blob/master/Art/cover.png" width="320">

## Dependency

*Step 1*. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
  
*Step 2*. Add the dependency

```gradle
	dependencies {
	        implementation 'com.github.divyanshub024:AndroidDraw:v0.1'
	}
  
  ```
## Usage

There are two ways to use this library.

### 1. Use Activity

You can call the `Drawing Activity` using `startActivityForResult` which will return you bitmap in byteArray. By using this method you will have all the features like change strokeWidth, change strokeColor, change Alpha, erase, redo, undo.

```kotlin
val intent = Intent(this, DrawingActivity::class.java)
startActivityForResult(intent, REQUEST_CODE_DRAW)

// Get bitmap in onActivityResult
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
```
### 2.Use DrawView

<img src="https://github.com/divyanshub024/AndroidDraw/blob/master/Art/banner.png" width="320">

#### Declaration
```xml
<com.divyanshu.draw.widget.DrawView
        android:id="@+id/draw_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

- To clear canvas
```
draw_view.clearCanvas()
```
- To set stroke width
```kotlin
// takes input as Float
draw_view.setStrokeWidth(strokeWidth)
```
- To set stroke alpha
```kotlin
// takes input as Int
draw_view.setAlpha(progress)
```
- To set stroke color
```
draw_view.setColor(color)
```
- To undo
```
draw_view.undo()
```

- To redo
```
draw_view.redo()
```
## LICENCE
```
Copyright 2018 Divyanshu Bhargava

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
