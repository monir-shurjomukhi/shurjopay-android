package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityLoginBinding
import com.shurjomukhi.shurjopay.model.ForgotPassword
import com.shurjomukhi.shurjopay.model.Login
import com.shurjomukhi.shurjopay.ui.viewmodel.LoginViewModel
import com.shurjomukhi.shurjopay.utils.MOBILE_NUMBER
import com.shurjomukhi.shurjopay.utils.VERIFICATION_TYPE
import com.shurjomukhi.shurjopay.utils.VERIFY_ACCOUNT

class LoginActivity : BaseActivity() {

  private lateinit var binding: ActivityLoginBinding
  private lateinit var viewModel: LoginViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    binding.registerTextView.setOnClickListener {
      startActivity(Intent(this, RegistrationActivity::class.java))
    }

    binding.forgotPasswordTextView.setOnClickListener {
      startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    binding.loginButton.setOnClickListener {
      //startActivity(Intent(this, MainActivity::class.java))
      login()
    }

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
    viewModel.login.observe(this, {
      when {
        it.message.equals("1") -> {
          preference.putMobileNumber(it.mobile_no)
          startActivity(Intent(this, MainActivity::class.java))
        }
        it.message.equals("2") -> {
          shortSnack(binding.root, R.string.mobile_number_or_password_did_not_match)
        }
        it.message.equals("3") -> {
          actionSnack(binding.root, R.string.you_do_not_have_an_account, R.string.register) {
            startActivity(Intent(this, RegistrationActivity::class.java))
          }
        }
        it.message.equals("0") -> {
          actionSnack(binding.root, R.string.your_account_is_not_active, R.string.activate) {
            //startActivity(Intent(this, LoginActivity::class.java))
            activate()
          }
        }
      }
    })
    viewModel.forgotPassword.observe(this, {
      if (it.message.equals("1")) {
        val intent = Intent(this, VerificationActivity::class.java)
        intent.putExtra(MOBILE_NUMBER, binding.phoneLayout.editText?.text.toString())
        intent.putExtra(VERIFICATION_TYPE, VERIFY_ACCOUNT)
        startActivity(intent)
      }
    })
  }

  private fun login() {
    val phone = binding.phoneLayout.editText?.text.toString()
    val password = binding.passwordLayout.editText?.text.toString()

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
    if (password.isEmpty()) {
      binding.passwordLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.passwordLayout.error = null
    }

    val login = Login(phone, password, null, null)
    viewModel.login(login)
  }

  private fun activate() {
    val forgotPassword = ForgotPassword(binding.phoneLayout.editText?.text.toString(), null)
    viewModel.forgotPassword(forgotPassword)
  }
}
