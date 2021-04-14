package com.dngwjy.githublist.ui.favourite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dngwjy.githublist.R
import com.dngwjy.githublist.abstraction.LiveDataState
import com.dngwjy.githublist.abstraction.RvAdapter
import com.dngwjy.githublist.abstraction.ShowFavourites
import com.dngwjy.githublist.databinding.ActivityFavouriteBinding
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.domain.User
import android.content.Intent
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteActivity : AppCompatActivity(),Observer<LiveDataState> {
    private lateinit var binding:ActivityFavouriteBinding
    private val listFavourite= mutableListOf<User>()
    private val adapter=object : RvAdapter<User,ItemUserBinding>(listFavourite,{
        val intent =
            Intent(this, com.dngwjy.githublist.ui.detail.DetailActivity::class.java)
        intent.putExtra("data", it)
        startActivity(intent)
    }){
        override fun getViewBinding(parent: ViewGroup, viewType: Int): ItemUserBinding =
            ItemUserBinding.inflate(layoutInflater, parent, false)

        override fun layoutId(position: Int, obj: User): Int = R.layout.item_user

        override fun viewHolder(binding: ItemUserBinding, viewType: Int): RecyclerView.ViewHolder =
            FavouriteVH(binding)

    }

    override fun onResume() {
        super.onResume()
        favouriteViewModel.getFavourite()
    }
    private val favouriteViewModel by viewModel<FavouriteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        favouriteViewModel.liveDataState.observe(this,this)
        binding= ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.rvFavourite.apply {
            layoutManager=LinearLayoutManager(this@FavouriteActivity)
            adapter=this@FavouriteActivity.adapter
        }
    }

    override fun onChanged(t: LiveDataState?) {
        when(t){
            is ShowFavourites->{
                listFavourite.clear()
                listFavourite.addAll(t.data)
                adapter.refreshData(listFavourite)
            }
            else->{

            }
        }
    }


}