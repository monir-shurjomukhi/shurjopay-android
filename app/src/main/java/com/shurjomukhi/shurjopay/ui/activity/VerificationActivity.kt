package com.shurjomukhi.shurjopay.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
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

    binding.otp1Layout.editText?.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text?.isNotEmpty() == true) {
          binding.otp2Layout.editText?.requestFocus()
        }
      }

      override fun afterTextChanged(p0: Editable?) {}
    })

    binding.otp2Layout.editText?.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text?.isNotEmpty() == true) {
          binding.otp3Layout.editText?.requestFocus()
        } else {
          binding.otp1Layout.editText?.requestFocus()
        }
      }

      override fun afterTextChanged(p0: Editable?) {}
    })

    binding.otp3Layout.editText?.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text?.isNotEmpty() == true) {
          binding.otp4Layout.editText?.requestFocus()
        } else {
          binding.otp2Layout.editText?.requestFocus()
        }
      }

      override fun afterTextChanged(p0: Editable?) {}
    })

    binding.otp4Layout.editText?.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text?.isEmpty() == true) {
          binding.otp3Layout.editText?.requestFocus()
        }
      }

      override fun afterTextChanged(p0: Editable?) {}
    })
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
