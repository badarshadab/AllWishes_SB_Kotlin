package com.examp.allwishes.ui.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import com.examp.allwishes.R
import com.google.android.material.textfield.TextInputEditText


class AddText(private val colorPicker: PicColor) {
    private val fonts =
        arrayOf("1.ttf", "2.ttf", "3.ttf", "4.ttf", "5.ttf", "6.ttf", "7.ttf", "8.ttf")

    operator fun invoke(
        context: Context,
        callback: (result: Triple<String, Int, Typeface>) -> Unit
    ) {
        addTextDialog(context, true) { triple ->
            callback(triple)
        }
    }

    private fun addTextDialog(
        context: Context,
        isCancelable: Boolean,
        callback: (result: Triple<String, Int, Typeface>) -> Unit
    ) {
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelable)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.add_text)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val etInput: TextInputEditText = dialog.findViewById(R.id.etInput)
        dialog.findViewById<View>(R.id.ivFont).setOnClickListener { v: View? ->
            showFontDialog(
                v?.context,
                true
            ) {
                etInput.setTypeface(it)
            }
        }
        dialog.findViewById<View>(R.id.ivColor).setOnClickListener { v: View? ->
            colorPicker.showColorPickerDialog(context) {
                etInput.setTextColor(it)
            }
        }
        dialog.findViewById<View>(R.id.btnAdd).setOnClickListener { v: View? ->
            dialog.dismiss()
            val text = etInput.text.toString()
            val color = etInput.currentTextColor
            val fontface = etInput.typeface

            val triple = Triple(text, color, fontface)
            callback(triple)
        }
        dialog.findViewById<View>(R.id.btnCancel)
            .setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    private fun showFontDialog(
        context: Context?,
        isCancelable: Boolean,
        callback: (result: Typeface) -> Unit
    ) {
        val dialog = Dialog(context!!, R.style.Theme_Dialog)
        dialog.setCancelable(isCancelable)
        dialog.setCanceledOnTouchOutside(isCancelable)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.font_dialog_layout)
        val lv = dialog.findViewById<ListView>(R.id.lv)
        lv.adapter = com.examp.allwishes.ui.adapter.FontSpinnerAdapter(
            context,
            R.layout.spinner_row,
            fonts
        )
        lv.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                val face = Typeface.createFromAsset(
                    context.assets,
                    fonts.get(position)
                )
                callback(face)
                dialog.dismiss()
            }
        dialog.show()
    }

//    private fun showColorPickerDialog(editText: EditText) {
//        val colorPickerDialog = ColorPickerDialog(editText.context,
//            R.color.colorOrange, object : ColorPickerDialog.OnColorSelectedListener{
//                override fun onColorSelected(color: Int) {
//                    editText.setTextColor(color)
//                }
//            })
//        colorPickerDialog.show()
//    }
}