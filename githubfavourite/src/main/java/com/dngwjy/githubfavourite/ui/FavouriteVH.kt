package com.dngwjy.githubfavourite.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dngwjy.githubfavourite.abstraction.RvAdapter
import com.dngwjy.githubfavourite.data.FavouriteUser
import com.dngwjy.githubfavourite.databinding.ItemUserBinding

class FavouriteVH(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root),
        RvAdapter.Binder<FavouriteUser> {
    override fun bindData(data: FavouriteUser, listen: (FavouriteUser) -> Unit, position: Int) {
        binding.tvName.text = data.login
        Glide.with(itemView).load(data.avatar_url).circleCrop().into(binding.ivAvatar)
        itemView.setOnClickListener { listen(data) }
    }
}