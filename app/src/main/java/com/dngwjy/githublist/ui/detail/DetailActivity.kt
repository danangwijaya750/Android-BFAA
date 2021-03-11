package com.dngwjy.githublist.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dngwjy.githublist.databinding.ActivityDetailBinding
import com.dngwjy.githublist.domain.User

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
        if(data!=null) {
            supportActionBar?.title = data.login
            binding.tvName.text = data.login
            binding.tvCompany.text = data.type
            binding.tvRepository.text = StringBuilder("Repositories : ").append(data.repos_url)
            Glide.with(this).load(data.avatar_url).centerCrop().into(binding.ivAvatar)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}