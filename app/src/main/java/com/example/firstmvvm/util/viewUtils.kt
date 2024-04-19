package com.example.firstmvvm.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun View.snakeBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snakebar ->
        snakebar.setAction("Ok") {
            snakebar.dismiss()
        }
    }.show()
}