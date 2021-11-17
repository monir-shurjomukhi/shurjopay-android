package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityChangePasswordBinding
import com.shurjomukhi.shurjopay.model.ChangePassword
import com.shurjomukhi.shurjopay.ui.viewmodel.ChangePasswordViewModel
import com.shurjomukhi.shurjopay.utils.MOBILE_NUMBER

class ChangePasswordActivity : BaseActivity() {

  private lateinit var binding: ActivityChangePasswordBinding
  private lateinit var viewModel: ChangePasswordViewModel
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

    viewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
    mobileNumber = intent.getStringExtra(MOBILE_NUMBER).toString()

    viewModel.progress.observe(this, {
      if (it) {
        showProgress()
      } else {
        hideProgress()
      }
    })
    viewModel.message.observe(this, {
      shortSnack(binding.root, it)
    })
    viewModel.changePassword.observe(this, {
      if (it.message.equals("1")) {
        actionSnack(binding.root, R.string.password_changed_successfully, R.string.login) {
          startActivity(Intent(this, LoginActivity::class.java))
        }
      } else {
        shortSnack(binding.root, R.string.something_went_wrong)
      }
    })

    binding.changePasswordButton.setOnClickListener {
      changePassword()
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
    viewModel.changePassword(changePassword)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
