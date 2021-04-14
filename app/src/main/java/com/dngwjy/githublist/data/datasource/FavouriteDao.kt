package com.dngwjy.githublist.data.datasource

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dngwjy.githublist.data.local.FavouriteUser
import io.reactivex.Observable

@Dao
interface FavouriteDao {
    @Insert
    fun insert(favouriteUser: FavouriteUser)

    @Query("select * from tbl_favourite")
    fun getData():Observable<List<FavouriteUser>>

    @Delete
    fun removeFavourite(favouriteUser: FavouriteUser)

    @Query("select * from tbl_favourite where id = :id ")
    fun checkData(id: Int): Observable<List<FavouriteUser>>

    @Query("select * from tbl_favourite")
    fun getFavourites(): Cursor
}