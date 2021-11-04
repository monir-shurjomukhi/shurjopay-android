package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.shurjomukhi.shurjopay.networking.ApiClient
import com.shurjomukhi.shurjopay.networking.ApiInterface

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
  val progress = MutableLiveData<Boolean>()
  val message = MutableLiveData<Int>()
  //val preference = VetPreference(application)
  val apiClient = ApiClient().getApiClient("http://qr.shurjopay.com.bd/")?.create(ApiInterface::class.java)
}
