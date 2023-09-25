package com.mehedisoftdev.productsapps.utils

import android.content.Context
import android.widget.Toast

object AppManager {
    fun showMessage(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }

}