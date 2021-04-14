package com.dngwjy.githublist.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    companion object {
        val PREFS_FILENAME = "com.dngwjy.githublist"
        val DAILY = "daily"
    }

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var daily: Boolean
        get() = prefs.getBoolean(DAILY, false)
        set(value) = prefs.edit().putBoolean(DAILY, value).apply()

}