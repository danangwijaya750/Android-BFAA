package com.dngwjy.githublist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dngwjy.githublist.R
import com.dngwjy.githublist.abstraction.*
import com.dngwjy.githublist.databinding.ActivityMainBinding
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.ui.detail.*
import com.dngwjy.githublist.util.logD
import com.dngwjy.githublist.util.logE
import com.dngwjy.githublist.util.toGone
import com.dngwjy.githublist.util.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity(),Observer<LiveDataState> {
    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val listUser = mutableListOf<User>()
    private val adapter = object : RvAdapter<User, ItemUserBinding>(listUser, {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("data", it)
        startActivity(intent)
    }) {
        override fun layoutId(position: Int, obj: User): Int = R.layout.item_user

        override fun viewHolder(binding: ItemUserBinding, viewType: Int): RecyclerView.ViewHolder =
            UserVH(binding)

        override fun getViewBinding(parent: ViewGroup, viewType: Int): ItemUserBinding =
            ItemUserBinding.inflate(layoutInflater, parent, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.liveDataState.observe(this,this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRv()
        binding.svUser.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrEmpty()){
                    getData()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun setupRv() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun getData() {
        mainViewModel.searchUser(binding.svUser.query.toString())
    }

    override fun onChanged(t: LiveDataState?) {
        when(t){
            is IsLoading->{
                if(t.state){
                    binding.pbLoading.toVisible()
                }else{
                    binding.pbLoading.toGone()
                }
            }
            is IsError->{
                logE(t.msg)
            }
            is ShowSearchUser->{
                logD(t.data.size.toString())
                listUser.clear()
                listUser.addAll(t.data)
                adapter.refreshData(listUser)
            }
            else -> {

            }
        }

    }
}