package com.shurjomukhi.shurjopay.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
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

    binding.otp1Layout.editText?.requestFocus()

    binding.otp1Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (count != 0) {
        binding.otp2Layout.editText?.requestFocus()
      }
    }

    binding.otp2Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (count == 0) {
        binding.otp1Layout.editText?.requestFocus()
      } else {
        binding.otp3Layout.editText?.requestFocus()
      }
    }

    binding.otp3Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (count == 0) {
        binding.otp2Layout.editText?.requestFocus()
      } else {
        binding.otp4Layout.editText?.requestFocus()
      }
    }

    binding.otp4Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (count == 0) {
        binding.otp3Layout.editText?.requestFocus()
      } else {
        binding.otp5Layout.editText?.requestFocus()
      }
    }

    binding.otp5Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (count == 0) {
        binding.otp4Layout.editText?.requestFocus()
      } else {
        binding.otp6Layout.editText?.requestFocus()
      }
    }

    binding.otp6Layout.editText?.doOnTextChanged { text, start, before, count ->
      if (count == 0) {
        binding.otp5Layout.editText?.requestFocus()
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
