package com.shurjomukhi.shurjopay.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.core.widget.doOnTextChanged
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

  private lateinit var binding: ActivityVerificationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityVerificationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    binding.otp1Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (text.toString().isNotEmpty()) {
        binding.otp2Layout.editText?.requestFocus()
      }
    }

    binding.otp2Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (text.toString().isEmpty()) {
        binding.otp1Layout.editText?.requestFocus()
      } else {
        binding.otp3Layout.editText?.requestFocus()
      }
    }

    binding.otp3Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (text.toString().isEmpty()) {
        binding.otp2Layout.editText?.requestFocus()
      } else {
        binding.otp4Layout.editText?.requestFocus()
      }
    }

    binding.otp4Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (text.toString().isEmpty()) {
        binding.otp3Layout.editText?.requestFocus()
      }
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
