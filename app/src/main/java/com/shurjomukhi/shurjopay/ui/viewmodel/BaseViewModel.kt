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
  val apiClient: ApiInterface =
    ApiClient().getApiClient("https://engine.shurjopayment.com/api/")
      .create(ApiInterface::class.java)
  val apiClientQR: ApiInterface =
    ApiClient().getApiClient("https://qr.shurjopay.com.bd/api/").create(ApiInterface::class.java)
  val apiClientQR2: ApiInterface =
    ApiClient().getApiClient("http://192.168.68.154:8080/api/").create(ApiInterface::class.java)
}
