<img src="https://github.com/divyanshub024/AndroidDraw/blob/master/Art/AndroidDraw.png">

# Android Draw [![](https://jitpack.io/v/divyanshub024/AndroidDraw.svg)](https://jitpack.io/#divyanshub024/AndroidDraw) [![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-AndroidDraw-green.svg?style=flat )]( https://android-arsenal.com/details/1/7150 )

A drawing view for your android application							

<img src="https://github.com/divyanshub024/AndroidDraw/blob/master/Art/cover.png" width="320">

### Download

For information : checkout [Sample App Code](https://github.com/divyanshub024/AndroidDraw/tree/master/app) in repository.

<a href='https://play.google.com/store/apps/details?id=com.divyanshu.androiddraw&rdid=com.divyanshu.androiddraw&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width = "320"/></a>

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

## Contributor
Thanks [Arslan Şahin](https://github.com/Arslanshn) for this amazing logo.

## Connect
- [Twitter](https://twitter.com/divyanshub024)
- [Medium](https://medium.com/@divyanshub024)
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
