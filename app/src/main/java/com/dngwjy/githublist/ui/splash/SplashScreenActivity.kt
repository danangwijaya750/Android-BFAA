package com.dngwjy.githublist.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.dngwjy.githublist.databinding.ActivitySplashScreenBinding
import com.dngwjy.githublist.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        doSplash()
    }

    private fun doSplash() {
        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
            .subscribe {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            })

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }
}