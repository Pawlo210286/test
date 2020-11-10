package com.test.test.common

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class MessageProcessor {

    private var activity: WeakReference<AppCompatActivity> = initActivity()

    private fun initActivity() = WeakReference<AppCompatActivity>(null)

    fun attach(activity: AppCompatActivity) {
        this.activity = WeakReference(activity)
    }

    fun showToast(message: String) {
        activity.get()?.let {
            it.runOnUiThread {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}