package com.shurjomukhi.shurjopay.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityRegistrationBinding
import com.shurjomukhi.shurjopay.model.Registration
import com.shurjomukhi.shurjopay.ui.viewmodel.RegistrationViewModel

class RegistrationActivity : BaseActivity() {

  private lateinit var binding: ActivityRegistrationBinding
  private lateinit var viewModel: RegistrationViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegistrationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

    binding.loginTextView.setOnClickListener {
      onBackPressed()
    }

    binding.registerButton.setOnClickListener {
      register()
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

    viewModel.registration.observe(this, {
      it.let {
        if (it.message.equals("1")) {
          actionSnack(binding.root, R.string.registration_successful, R.string.login, object : View.OnClickListener {
            override fun onClick(p0: View?) {
              onBackPressed()
            }
          })
        } else if (it.message.equals("0")) {
          shortSnack(binding.root, R.string.your_mobile_number_is_already_in_use)
        }
      }
    })
  }

  private fun register() {
    val name = binding.nameLayout.editText?.text.toString()
    val email = binding.emailLayout.editText?.text.toString()
    val phone = binding.phoneLayout.editText?.text.toString()
    val password = binding.passwordLayout.editText?.text.toString()
    val retypePassword = binding.retypePasswordLayout.editText?.text.toString()

    if (name.isEmpty()) {
      binding.nameLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.nameLayout.error = null
    }
    if (phone.isEmpty()) {
      binding.phoneLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.phoneLayout.error = null
    }
    if (phone.length != 11) {
      binding.phoneLayout.error = getString(R.string.this_field_is_required)
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
    if (retypePassword.isEmpty()) {
      binding.retypePasswordLayout.error = getString(R.string.this_field_is_required)
      return
    } else {
      binding.retypePasswordLayout.error = null
    }
    if (retypePassword != password) {
      binding.retypePasswordLayout.error = getString(R.string.passwords_did_not_match)
      return
    } else {
      binding.retypePasswordLayout.error = null
    }

    val registration = Registration(name, email, phone, password, retypePassword, null)
    viewModel.register(registration)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
