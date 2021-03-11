package com.dngwjy.githublist.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dngwjy.githublist.abstraction.RvAdapter
import com.dngwjy.githublist.data.User
import com.dngwjy.githublist.databinding.ItemUserBinding

class UserVH(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root),
    RvAdapter.Binder<User> {
    override fun bindData(data: User, listen: (User) -> Unit, position: Int) {
        binding.tvName.text = data.name
        binding.tvCompany.text = data.company
        binding.tvUsername.text = data.username
        val imageName = data.avatar.split("/").toTypedArray()
        val res = itemView.context.resources.getIdentifier(
            imageName[1],
            "drawable",
            itemView.context.packageName
        )
        Glide.with(itemView).load(itemView.resources.getDrawable(res)).circleCrop()
            .into(binding.ivAvatar)
        itemView.setOnClickListener { listen(data) }
    }
}