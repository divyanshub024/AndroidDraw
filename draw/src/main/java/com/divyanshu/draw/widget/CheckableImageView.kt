package com.divyanshu.draw.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Checkable
import android.widget.ImageView

class CheckableImageView(context: Context, attributeSet: AttributeSet): ImageView(context, attributeSet), Checkable {

    private var mChecked = false

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun setChecked(b: Boolean) {
        if (b != mChecked) {
            mChecked = b
            refreshDrawableState()
        }
    }
}