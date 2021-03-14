package com.dngwjy.githublist.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dngwjy.githublist.abstraction.RvAdapter
import com.dngwjy.githublist.databinding.ItemUserBinding
import com.dngwjy.githublist.domain.User

class UserVH(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root),
    RvAdapter.Binder<User> {
    override fun bindData(data: User, listen: (User) -> Unit, position: Int) {
        binding.tvName.text = data.login
        Glide.with(itemView).load(data.avatar_url).circleCrop().into(binding.ivAvatar)
        itemView.setOnClickListener { listen(data) }
    }
}