package com.shurjomukhi.shurjopay.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.databinding.FragmentProfileBinding
import com.shurjomukhi.shurjopay.ui.activity.LoginActivity
import com.shurjomukhi.shurjopay.ui.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

  private lateinit var binding: FragmentProfileBinding
  private lateinit var viewModel: ProfileViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
    viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    /*viewModel.progress.observe(viewLifecycleOwner, {
      if (it) {
        showProgress()
      } else {
        hideProgress()
      }
    })
    viewModel.message.observe(viewLifecycleOwner, {
      shortSnack(binding.root, it)
    })*/
    viewModel.logout.observe(viewLifecycleOwner, {
      if (it.message.equals("1")) {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finishAffinity()
      }
    })

    binding.logoutCardView.setOnClickListener {
      viewModel.logout()
    }
  }
}
