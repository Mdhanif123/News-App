package com.example.newsapp

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("Default Mode", 0)

    var backgroundMode: Boolean
        get() = prefs.getBoolean("default mode", false)
        set(value) = prefs.edit().putBoolean("default mode", value).apply()
}