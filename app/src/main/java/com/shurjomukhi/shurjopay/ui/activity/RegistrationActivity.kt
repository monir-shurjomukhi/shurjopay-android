package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityLoginBinding
import com.shurjomukhi.shurjopay.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

  private lateinit var binding: ActivityRegistrationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegistrationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    binding.loginTextView.setOnClickListener {
      onBackPressed()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}