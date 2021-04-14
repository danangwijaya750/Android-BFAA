package com.dngwjy.githublist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dngwjy.githublist.R
import com.dngwjy.githublist.abstraction.*
import com.dngwjy.githublist.data.local.SharedPref
import com.dngwjy.githublist.data.service.DailyReminderService
import com.dngwjy.githublist.databinding.ActivityMainBinding
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.databinding.LayoutSettingsBinding
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.ui.detail.*
import com.dngwjy.githublist.ui.favourite.FavouriteActivity
import com.dngwjy.githublist.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity(), Observer<LiveDataState> {
    private val mainViewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val listUser = mutableListOf<User>()
    private lateinit var sharedPref:SharedPref
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
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        mainViewModel.liveDataState.observe(this, this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        sharedPref= SharedPref(this)
        setupRv()
        binding.svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    getData()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        binding.ivSettings.setOnClickListener {
            showDialog()
        }
        binding.ivFavourite.setOnClickListener {
            startActivity(Intent(this,FavouriteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (!binding.svUser.query.isNullOrEmpty()) {
            getData()
        }
    }

    private fun showDialog(){
        val dialogBinding=LayoutSettingsBinding.inflate(layoutInflater)
        val builder=AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        dialogBinding.toggleReminder.isChecked = sharedPref.daily
        dialogBinding.toggleReminder.setOnClickListener {
            sharedPref.daily=!sharedPref.daily
            DailyReminderService().setRepeatingAlarm(this,"09:00",sharedPref.daily)
            logE(sharedPref.daily.toString())
        }
        builder.create().show()

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
        when (t) {
            is IsLoading -> {
                if (t.state) {
                    binding.pbLoading.toVisible()
                } else {
                    binding.pbLoading.toGone()
                }
            }
            is IsError -> {
                logE(t.msg)
                toast(t.msg)
            }
            is ShowSearchUser -> {
                listUser.clear()
                listUser.addAll(t.data)
                adapter.refreshData(listUser)
            }
            else -> {

            }
        }
    }
}