package com.andreesperanca.deolhonobus.util

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
fun View.hideKeyBoard(view: View) {
    windowInsetsController?.hide(WindowInsets.Type.ime())
    view.clearFocus()
}
