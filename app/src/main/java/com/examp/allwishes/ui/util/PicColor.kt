package com.examp.allwishes.ui.util

import android.content.Context
import com.examp.allwishes.R


class PicColor {
    fun showColorPickerDialog(context: Context, callback: (result: Int) -> Unit) {
        val colorPickerDialog =
            ColorPickerDialog(context,
                R.color.colorOrange,
                object :
                    ColorPickerDialog.OnColorSelectedListener {
                    override fun onColorSelected(color: Int) {
                        callback(color)
                    }
                })
        colorPickerDialog.show()
    }
}