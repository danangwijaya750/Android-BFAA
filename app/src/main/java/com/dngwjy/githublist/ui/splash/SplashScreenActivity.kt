package com.dngwjy.githublist.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.dngwjy.githublist.databinding.ActivitySplashScreenBinding
import com.dngwjy.githublist.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        handler = Handler()
        doSplash()
    }

    private fun doSplash() {
        handler?.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler = null
    }
}