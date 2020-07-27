package com.shurjomukhi.shurjopay.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionsViewModel : ViewModel() {

  private val _text = MutableLiveData<String>().apply {
    value = "This is Transactions Fragment"
  }
  val text: LiveData<String> = _text
}
