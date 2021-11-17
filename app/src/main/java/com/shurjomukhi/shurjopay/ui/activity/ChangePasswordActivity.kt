package com.shurjomukhi.shurjopay.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityChangePasswordBinding
import com.shurjomukhi.shurjopay.model.ChangePassword
import com.shurjomukhi.shurjopay.utils.MOBILE_NUMBER

class ChangePasswordActivity : AppCompatActivity() {

  private lateinit var binding: ActivityChangePasswordBinding

  private lateinit var mobileNumber: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityChangePasswordBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    mobileNumber = intent.getStringExtra(MOBILE_NUMBER).toString()

    binding.changePasswordButton.setOnClickListener {

    }
  }

  private fun changePassword() {
    val password = binding.newPasswordLayout.editText?.text.toString()
    val retypePassword = binding.retypeNewPasswordLayout.editText?.text.toString()

    if (password.isEmpty()) {
      binding.newPasswordLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.newPasswordLayout.error = null
    }
    if (retypePassword.isEmpty()) {
      binding.retypeNewPasswordLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.retypeNewPasswordLayout.error = null
    }
    if (retypePassword != password) {
      binding.retypeNewPasswordLayout.error = getString(R.string.passwords_did_not_match)
      return
    } else {
      binding.retypeNewPasswordLayout.error = null
    }

    val changePassword = ChangePassword(password, mobileNumber, null)
  }
}
