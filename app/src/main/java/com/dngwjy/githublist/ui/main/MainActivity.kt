package com.dngwjy.githublist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dngwjy.githublist.R
import com.dngwjy.githublist.abstraction.RvAdapter
import com.dngwjy.githublist.data.User
import com.dngwjy.githublist.databinding.ActivityMainBinding
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.ui.detail.*
import com.dngwjy.githublist.util.logE
import com.dngwjy.githublist.util.readData

class MainActivity : AppCompatActivity() {
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRv()
        getData()
    }

    private fun setupRv() {
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun getData() {
        listUser.clear()
        listUser.addAll(readData(R.raw.githubuser))
        adapter.refreshData(listUser)
        logE(listUser.size.toString())
    }
}