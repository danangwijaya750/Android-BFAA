package com.dngwjy.githublist.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dngwjy.githublist.data.User
import com.dngwjy.githublist.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showData()
    }

    private fun showData() {
        val data = intent.getParcelableExtra<User>("data")
        supportActionBar?.title = data?.username
        binding.tvName.text = data?.name
        binding.tvCompany.text = data?.company
        binding.tvUsername.text = data?.username
        binding.tvRepository.text = "Repositories : ${data?.repository}"
        binding.tvFollow.text = "Followers : ${data?.follower} Following :${data?.following}"
        val imageName = data?.avatar!!.split("/").toTypedArray()
        val res = resources.getIdentifier(imageName[1], "drawable", packageName)
        Glide.with(this).load(resources.getDrawable(res)).centerCrop().into(binding.ivAvatar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}