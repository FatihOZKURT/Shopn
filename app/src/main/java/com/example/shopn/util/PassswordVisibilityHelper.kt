package com.example.shopn.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.shopn.R

class PasswordVisibilityHelper(
    private val context: Context,
    private val editText: EditText
) {

    private var isPasswordVisible = false

    init {
        setupToggle()
        setIcon(isPasswordVisible)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupToggle() {
        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = editText.compoundDrawablesRelative[2]
                if (drawableEnd != null) {
                    val drawableWidth = drawableEnd.bounds.width()
                    val touchAreaStart = editText.width - editText.paddingEnd - drawableWidth
                    if (event.x >= touchAreaStart) {
                        togglePasswordVisibility()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        val selection = editText.selectionEnd

        if (isPasswordVisible) {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        editText.setSelection(selection)
        setIcon(isPasswordVisible)
    }

    private fun setIcon(visible: Boolean) {
        val icon = if (visible) R.drawable.visibility_on else R.drawable.visibility_off
        val drawable = ContextCompat.getDrawable(context, icon)
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
    }
}