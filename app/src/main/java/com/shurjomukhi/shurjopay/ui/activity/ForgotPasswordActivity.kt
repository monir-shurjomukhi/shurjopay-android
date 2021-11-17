package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityForgotPasswordBinding
import com.shurjomukhi.shurjopay.model.ForgotPassword
import com.shurjomukhi.shurjopay.ui.viewmodel.ForgotPasswordViewModel
import com.shurjomukhi.shurjopay.utils.FORGOT_PASSWORD
import com.shurjomukhi.shurjopay.utils.MOBILE_NUMBER
import com.shurjomukhi.shurjopay.utils.VERIFICATION_TYPE

class ForgotPasswordActivity : BaseActivity() {

  private lateinit var binding: ActivityForgotPasswordBinding
  private lateinit var viewModel: ForgotPasswordViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)

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
    viewModel.forgotPassword.observe(this, {
      if (it.message.equals("1")) {
        val intent = Intent(this, VerificationActivity::class.java)
        intent.putExtra(MOBILE_NUMBER, binding.phoneLayout.editText?.text.toString())
        intent.putExtra(VERIFICATION_TYPE, FORGOT_PASSWORD)
        startActivity(intent)
      }
    })

    binding.forgotPasswordButton.setOnClickListener {
      forgotPassword()
    }
  }

  private fun forgotPassword() {
    val phone = binding.phoneLayout.editText?.text.toString()

    if (phone.isEmpty()) {
      binding.phoneLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.phoneLayout.error = null
    }
    if (phone.length != 11) {
      binding.phoneLayout.error = getString(R.string.mobile_number_is_invalid)
      return
    } else {
      binding.phoneLayout.error = null
    }

    val forgotPassword = ForgotPassword(phone, null)
    viewModel.forgotPassword(forgotPassword)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
