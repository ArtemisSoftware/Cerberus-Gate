package com.artemissoftware.cerberusgate.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


class SoftKeyboardUtils {


    companion object {

        fun openSoftKeyboard(context: Context?, view: View?) {
            if (context != null && view != null) {
                view.requestFocus()
                val inputMethodManager: InputMethodManager = context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            }
        }


        fun closeSoftKeyboard(
            context: Context,
            view: View
        ) {
            val keyboard = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun closeSoftKeyboard(activity: Activity?) {
            if (activity == null) {
                return
            }
            val currentFocus = activity.currentFocus
            currentFocus?.let { closeSoftKeyboard(activity, it) }
        }
    }
}