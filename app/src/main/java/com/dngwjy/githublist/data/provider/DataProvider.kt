package com.dngwjy.githublist.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dngwjy.githublist.data.datasource.FavouriteDatabase

class DataProvider : ContentProvider() {

    companion object{
        const val AUTH = "com.dngwjy.githublist"
    }
    val CODE_DIR= 1
    val CODE_ITEM=2
    private val MATCHER=UriMatcher(UriMatcher.NO_MATCH)

    init{
        MATCHER.addURI(AUTH, "tbl_favourite", CODE_DIR)
        MATCHER.addURI(AUTH, "tbl_favourite/*", CODE_ITEM)
    }

    private var db:FavouriteDatabase?=null

    override fun onCreate(): Boolean {
        db= FavouriteDatabase.getInstance(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code = MATCHER.match(uri)
        if (code == CODE_DIR || code == CODE_ITEM) {
            val dao = db?.favDao()
            var cursor: Cursor? = null
            if (code == CODE_DIR) {
                cursor = dao?.getFavourites()
            }
            cursor?.setNotificationUri(context?.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}