package com.oguzhan.karnavalcase.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.oguzhan.karnavalcase.util.Constants

fun ImageView.loadUrl(path:String)
{
    Glide.with(this)
        .load(Constants.IMAGE_BASE_URL+path)
        .into(this)
}


fun EditText.doOnTextChanged(onTextChanged: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}