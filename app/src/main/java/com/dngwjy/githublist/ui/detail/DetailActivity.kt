package com.dngwjy.githublist.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.dngwjy.githublist.abstraction.IsError
import com.dngwjy.githublist.abstraction.IsLoading
import com.dngwjy.githublist.abstraction.LiveDataState
import com.dngwjy.githublist.abstraction.ShowDetailUser
import com.dngwjy.githublist.databinding.ActivityDetailBinding
import com.dngwjy.githublist.domain.DetailUser
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.ui.detail.follower.FollowerFragment
import com.dngwjy.githublist.ui.detail.following.FollowingFragment
import com.dngwjy.githublist.util.*
import kotlinx.coroutines.cancel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(), Observer<LiveDataState>, LifecycleObserver {

    private val detailViewModel by viewModel<DetailViewModel>()

    companion object {
        var username = ""
    }

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
        detailViewModel.liveDataState.observe(this, this)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupViewPager(){
        binding.vpFollow.adapter=ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.vpFollow)
    }

    private fun showData() {
        val data = intent.getParcelableExtra<User>("data")
        if (data != null) {
            Glide.with(this).load(data.avatar_url).circleCrop().into(binding.ivAvatar)
            binding.tvName.text = data.login
            username = data.login
        }
    }

    override fun onResume() {
        super.onResume()
        showData()
        detailViewModel.getUserDetail(username)
        setupViewPager()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onDestroy() {
        logE("destroyed")
        detailViewModel.viewModelScope.cancel()
        viewModelStore.clear()
        super.onDestroy()
    }


    override fun onChanged(t: LiveDataState?) {
        when (t) {
            is IsLoading -> {
                if (t.state) {
                    binding.pbLoading.toVisible()
                }else{
                    binding.pbLoading.toGone()
                }
            }
            is ShowDetailUser -> {
                showDetail(t.data)
            }
            is IsError -> {
                toast(t.msg)
            }
            else -> {

            }
        }
    }

    private fun showDetail(data: DetailUser) {
        logE("here")
        binding.tvRepository.text = data.repos_url
        binding.tvLocation.text = data.location
        binding.tvCompany.text = data.company
        binding.tvUsername.text = data.name
        binding.tvFollow.text = StringBuilder("Followers : ").append(data.followers)
                .append(" Following : ").append(data.following)
    }

    inner class ViewPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){
        private val titles = listOf("Followers","Following")
        private val pages = listOf<Fragment>(FollowerFragment.getInstance(),FollowingFragment.getInstance())
        override fun getCount(): Int = titles.size

        override fun getItem(position: Int): Fragment = pages[position]

        override fun getPageTitle(position: Int): CharSequence = titles[position]

    }


}