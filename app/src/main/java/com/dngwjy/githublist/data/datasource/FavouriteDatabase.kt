package com.dngwjy.githublist.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dngwjy.githublist.data.local.FavouriteUser

@Database(entities = [FavouriteUser::class],version = 1,exportSchema = false)
abstract class FavouriteDatabase : RoomDatabase(){
    abstract fun favDao():FavouriteDao
    companion object{
        private var instance:FavouriteDatabase?=null

        @Synchronized
        fun getInstance(ctx: Context): FavouriteDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext,FavouriteDatabase::class.java,"github-list.db")
                    .build()
            return instance!!

        }
        fun destroyInstance(){
            instance=null
        }
    }
}