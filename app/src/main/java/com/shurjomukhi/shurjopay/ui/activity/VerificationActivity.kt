package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityVerificationBinding
import com.shurjomukhi.shurjopay.model.Otp
import com.shurjomukhi.shurjopay.ui.viewmodel.OtpViewModel
import com.shurjomukhi.shurjopay.utils.MOBILE_NUMBER

class VerificationActivity : BaseActivity() {

  private lateinit var binding: ActivityVerificationBinding
  private lateinit var viewModel: OtpViewModel

  private lateinit var mobileNumber: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityVerificationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    viewModel = ViewModelProvider(this).get(OtpViewModel::class.java)

    mobileNumber = intent.getStringExtra(MOBILE_NUMBER).toString()

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

    binding.verifyButton.setOnClickListener {
      verifyOTP()
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

    viewModel.otp.observe(this, {
      if (it.message.equals("1")) {
        actionSnack(binding.root, R.string.registration_successful, R.string.login) {
          startActivity(Intent(this, LoginActivity::class.java))
        }
      } else if (it.message.equals("0")) {
        shortSnack(binding.root, R.string.otp_did_not_match)
      }
    })
  }

  private fun verifyOTP() {
    val otp1 = binding.otp1Layout.editText?.text.toString()
    val otp2 = binding.otp2Layout.editText?.text.toString()
    val otp3 = binding.otp3Layout.editText?.text.toString()
    val otp4 = binding.otp4Layout.editText?.text.toString()
    val otp5 = binding.otp5Layout.editText?.text.toString()
    val otp6 = binding.otp6Layout.editText?.text.toString()
    val otpString = otp1 + otp2 + otp3 + otp4 + otp5 + otp6

    if (otpString.length != 6) {
      shortSnack(binding.root, R.string.please_enter_6_digit_otp)
      return
    }

    val otp = Otp(otpString, mobileNumber, null)
    viewModel.verifyOTP(otp)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
