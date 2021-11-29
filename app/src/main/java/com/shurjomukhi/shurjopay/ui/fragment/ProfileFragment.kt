package com.shurjomukhi.shurjopay.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.databinding.FragmentProfileBinding
import com.shurjomukhi.shurjopay.ui.transactions.ProfileViewModel

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

    binding.logoutCardView.setOnClickListener {

    }
  }
}
