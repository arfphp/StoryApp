package com.example.storyappa.components

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyappa.R

class PasswordTextField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatEditText(context, attrs) {

    private var handler = Handler(Looper.getMainLooper())
    private val passwordValidationRunnable = Runnable {
        val text = text.toString()

        if (text.length > 0 && text.length < 8) {
            error = context.getString(R.string.error_password)
        }
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Remove any pending posts of passwordValidationRunnable and post a new one with a delay
                handler.removeCallbacks(passwordValidationRunnable)
                handler.postDelayed(passwordValidationRunnable, 500) // Delay for 500 milliseconds
            }
        })
    }
}