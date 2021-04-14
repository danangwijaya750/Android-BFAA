package com.dngwjy.githubfavourite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dngwjy.githubfavourite.R
import com.dngwjy.githubfavourite.abstraction.RvAdapter
import com.dngwjy.githubfavourite.data.FavouriteUser
import com.dngwjy.githubfavourite.databinding.ActivityMainBinding
import com.dngwjy.githubfavourite.databinding.ItemUserBinding

class MainActivity : AppCompatActivity() {
    val TAG="HERE"
    private val favouriteUsers= mutableListOf<FavouriteUser>()
    private lateinit var binding : ActivityMainBinding

    private val adapter=object:RvAdapter<FavouriteUser,ItemUserBinding>(favouriteUsers,{}){
        override fun getViewBinding(parent: ViewGroup, viewType: Int): ItemUserBinding
        = ItemUserBinding.inflate(layoutInflater)

        override fun layoutId(position: Int, obj: FavouriteUser): Int = R.layout.item_user

        override fun viewHolder(binding: ItemUserBinding, viewType: Int): RecyclerView.ViewHolder =
            FavouriteVH(binding)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvFavourite.apply {
            val layoutMan=LinearLayoutManager(this@MainActivity)
            layoutMan.orientation=LinearLayoutManager.VERTICAL
            layoutManager=layoutMan
            adapter=this@MainActivity.adapter
        }

        favouriteUsers.addAll(ContentResolverHelper(this).allFavouriteUser)
        Log.d(TAG, "onCreate: ${favouriteUsers.size}")
        adapter.notifyDataSetChanged()
    }




}