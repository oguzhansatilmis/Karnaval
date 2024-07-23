package com.oguzhan.karnavalcase

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.oguzhan.karnavalcase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isShowingDialog = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun showProgress() {
        isShowingDialog = true
        binding.activityProgressbarLayout.visibility = View.VISIBLE
    }

    fun hideProgress() {
        isShowingDialog = false
        binding.activityProgressbarLayout.visibility = View.GONE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isShowingDialog) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}