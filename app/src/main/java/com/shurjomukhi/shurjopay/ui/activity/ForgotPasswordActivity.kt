package com.shurjomukhi.shurjopay.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

  private lateinit var binding: ActivityForgotPasswordBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
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
