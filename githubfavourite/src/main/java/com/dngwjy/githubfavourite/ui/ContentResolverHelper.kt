package com.dngwjy.githubfavourite.ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.dngwjy.githubfavourite.data.FavouriteUser

class ContentResolverHelper(private val context: Context) {
    private var contentResolver: ContentResolver = context.contentResolver
    val uri= Uri.parse("content://com.dngwjy.githublist/tbl_favourite")
    val allFavouriteUser: List<FavouriteUser>
        get() {
            val favouriteUsers= mutableListOf<FavouriteUser>()
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    favouriteUsers.add(FavouriteUser(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7).toInt(),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13).toBoolean(),
                        cursor.getString(14),
                        cursor.getString(15),
                        cursor.getString(16),
                        cursor.getString(17)))
                }
            }
            return favouriteUsers
        }
}