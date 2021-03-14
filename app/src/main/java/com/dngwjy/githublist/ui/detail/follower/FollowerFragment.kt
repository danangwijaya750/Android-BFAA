package com.dngwjy.githublist.ui.detail.follower

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dngwjy.githublist.R
import com.dngwjy.githublist.abstraction.*
import com.dngwjy.githublist.databinding.FragmentFollowerBinding
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.ui.detail.DetailActivity
import com.dngwjy.githublist.ui.main.UserVH
import com.dngwjy.githublist.util.logE
import com.dngwjy.githublist.util.toGone
import com.dngwjy.githublist.util.toVisible
import kotlinx.coroutines.cancel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowerFragment : Fragment(R.layout.fragment_follower), Observer<LiveDataState> {

    companion object {
        fun getInstance(): FollowerFragment = FollowerFragment()
    }

    private lateinit var binding: FragmentFollowerBinding
    private val listFollower = mutableListOf<User>()
    private val adapter = object : RvAdapter<User, ItemUserBinding>(listFollower, {

    }) {
        override fun getViewBinding(parent: ViewGroup, viewType: Int): ItemUserBinding =
            ItemUserBinding.inflate(layoutInflater, parent, false)

        override fun layoutId(position: Int, obj: User): Int = R.layout.item_user

        override fun viewHolder(binding: ItemUserBinding, viewType: Int): RecyclerView.ViewHolder =
            UserVH(binding)

    }

    private val viewModel by viewModel<FollowerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveDataState.observe(viewLifecycleOwner, this)
        binding = FragmentFollowerBinding.bind(view)
        setupRv()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFollowers(DetailActivity.username)
    }

    private fun setupRv() {
        binding.rvFollower.apply {
            layoutManager = LinearLayoutManager(this@FollowerFragment.context)
            adapter = this@FollowerFragment.adapter
        }
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
            is ShowFollowers -> {
                showData(t.data)
            }
            is IsError -> {
                logE(t.msg)
            }
            else -> {

            }
        }
    }

    private fun showData(data: List<User>) {
        listFollower.clear()
        listFollower.addAll(data)
        adapter.refreshData(listFollower)
    }

    override fun onDestroy() {
        viewModel.viewModelScope.cancel()
        viewModelStore.clear()
        super.onDestroy()
    }

}