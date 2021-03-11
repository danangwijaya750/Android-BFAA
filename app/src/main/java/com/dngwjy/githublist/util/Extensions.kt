package com.dngwjy.githublist.util

import android.content.Context
import android.util.Log
import com.dngwjy.githublist.data.Data
import com.dngwjy.githublist.data.User
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

inline fun <reified T> T.logE(msg: String) = msg.let {
    Log.e(T::class.java.simpleName, it)
}

inline fun <reified T> T.logD(msg: String) = msg.let {
    Log.d(T::class.java.simpleName, it)
}

fun Context.readData(id: Int): List<User> {
    val inStream = this.resources.openRawResource(id)
    val bufferedReader = BufferedReader(InputStreamReader(inStream))
    val read = bufferedReader.use { it.readText() }
    val data = Gson().fromJson(read, Data::class.java)
    bufferedReader.close()
    return data.users
}
