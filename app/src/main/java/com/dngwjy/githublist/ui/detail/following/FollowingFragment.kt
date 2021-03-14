package com.dngwjy.githublist.ui.detail.following

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
import com.dngwjy.githublist.databinding.FragmentFollowingBinding
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.ui.detail.DetailActivity
import com.dngwjy.githublist.ui.main.UserVH
import com.dngwjy.githublist.util.logE
import com.dngwjy.githublist.util.toGone
import com.dngwjy.githublist.util.toVisible
import kotlinx.coroutines.cancel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment(R.layout.fragment_following), Observer<LiveDataState> {

    companion object {
        fun getInstance(): FollowingFragment = FollowingFragment()
    }


    private lateinit var binding: FragmentFollowingBinding
    private val viewModel by viewModel<FollowingViewModel>()

    private val listFollowing = mutableListOf<User>()

    private val adapter = object : RvAdapter<User, ItemUserBinding>(listFollowing, {

    }) {
        override fun getViewBinding(parent: ViewGroup, viewType: Int): ItemUserBinding =
            ItemUserBinding.inflate(layoutInflater, parent, false)

        override fun layoutId(position: Int, obj: User): Int = R.layout.item_user

        override fun viewHolder(binding: ItemUserBinding, viewType: Int): RecyclerView.ViewHolder =
            UserVH(binding)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.liveDataState.observe(viewLifecycleOwner, this)
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowingBinding.bind(view)

        setupRv()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFollowing(DetailActivity.username)
    }

    private fun setupRv() {
        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(this@FollowingFragment.context)
            adapter = this@FollowingFragment.adapter
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
            is ShowFollowing -> {
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
        listFollowing.clear()
        listFollowing.addAll(data)
        adapter.refreshData(listFollowing)
    }

    override fun onDestroy() {
        viewModel.viewModelScope.cancel()
        viewModelStore.clear()
        super.onDestroy()
    }
}