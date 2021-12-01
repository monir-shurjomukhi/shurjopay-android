package com.shurjomukhi.shurjopay.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.databinding.FragmentHomeBinding
import com.shurjomukhi.shurjopay.preference.SPPreference
import com.shurjomukhi.shurjopay.ui.activity.QRScannerActivity
import com.shurjomukhi.shurjopay.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
  protected lateinit var preference: SPPreference
  private lateinit var progressDialog: ProgressDialog

  private lateinit var binding: FragmentHomeBinding
  private lateinit var viewModel: HomeViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
    viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.scanCardView.setOnClickListener {
      startActivity(Intent(requireActivity(), QRScannerActivity::class.java))
    }
  }
}
