package com.shurjomukhi.shurjopay.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.FragmentProfileBinding
import com.shurjomukhi.shurjopay.ui.activity.LoginActivity
import com.shurjomukhi.shurjopay.ui.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

  private lateinit var binding: FragmentProfileBinding
  private lateinit var viewModel: ProfileViewModel
  private lateinit var progressDialog: ProgressDialog

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
    viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

    progressDialog = ProgressDialog(context)
    progressDialog.setMessage("Please Wait...")
    progressDialog.setCancelable(false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.progress.observe(viewLifecycleOwner, {
      if (it) {
        showProgress()
      } else {
        hideProgress()
      }
    })
    viewModel.message.observe(viewLifecycleOwner, {
      shortSnack(binding.root, it)
    })
    viewModel.logout.observe(viewLifecycleOwner, {
      if (it.message.equals("1")) {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finishAffinity()
      } else {
        shortSnack(binding.root, R.string.something_went_wrong)
      }
    })

    binding.logoutCardView.setOnClickListener {
      viewModel.logout()
    }
  }

  private fun showProgress() {
    progressDialog.show()
  }

  private fun hideProgress() {
    if (progressDialog.isShowing) {
      progressDialog.dismiss()
    }
  }

  private fun shortSnack(view: View, message: Int) {
    Snackbar.make(view, getString(message), Snackbar.LENGTH_SHORT).show()
  }
}
