package com.example.storyappa.components

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyappa.R

class EmailTextField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatEditText(context, attrs) {

    private var handler = Handler(Looper.getMainLooper())
    private val emailValidationRunnable = Runnable {
        val text = text.toString()

        if (text.length > 5 && !Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            error = context.getString(R.string.error_email)
        }
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                handler.removeCallbacks(emailValidationRunnable)
                handler.postDelayed(emailValidationRunnable, 500)
            }
        })
    }
}