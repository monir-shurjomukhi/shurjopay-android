package com.shurjomukhi.shurjopay.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shurjomukhi.shurjopay.R

class TransactionsFragment : Fragment() {

    private lateinit var transactionsViewModel: TransactionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_transactions, container, false)
        val textView: TextView = root.findViewById(R.id.text_transactions)
        transactionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}