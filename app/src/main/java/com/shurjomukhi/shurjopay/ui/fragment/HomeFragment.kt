package com.shurjomukhi.shurjopay.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.FragmentHomeBinding
import com.shurjomukhi.shurjopay.ui.activity.QRScannerActivity
import com.shurjomukhi.shurjopay.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

  private lateinit var binding: FragmentHomeBinding
  private lateinit var homeViewModel: HomeViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
    homeViewModel =
      ViewModelProviders.of(this).get(HomeViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_home, container, false)
    /*val textView: TextView = root.findViewById(R.id.text_profile)
    homeViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })*/
    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.scanCardView.setOnClickListener {
      startActivity(Intent(requireActivity(), QRScannerActivity::class.java))
    }
  }
}
