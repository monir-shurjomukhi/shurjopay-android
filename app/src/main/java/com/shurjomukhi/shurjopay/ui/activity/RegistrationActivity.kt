package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityLoginBinding
import com.shurjomukhi.shurjopay.databinding.ActivityRegistrationBinding
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
      startActivity(Intent(this, MainActivity::class.java))
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
          shortSnack(binding.root, R.string.unable_to_connect)
        } else if (it.message.equals("0")) {
          shortSnack(binding.root, R.string.your_mobile_number_is_already_in_use)
        }
      }
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
