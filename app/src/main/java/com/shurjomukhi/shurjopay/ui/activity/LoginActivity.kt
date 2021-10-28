package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.registerTextView.setOnClickListener {
      startActivity(Intent(this, RegistrationActivity::class.java))
    }
  }
}