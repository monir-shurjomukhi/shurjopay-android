package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.zxing.qrcode.encoder.QRCode
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.model.Registration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel(application: Application) : BaseViewModel(application) {
  private val _registration = MutableLiveData<Registration>()
  val registration: LiveData<Registration>
    get() = _registration

  fun register(registration: Registration) {
    progress.value = true
    apiClient?.register(registration)?.enqueue(object : Callback<Registration> {
      override fun onResponse(call: Call<Registration>, response: Response<Registration>) {
        if (response.isSuccessful) {
          _registration.value = response.body()
        } else {
          message.value = R.string.unable_to_connect
        }

        progress.value = false
      }

      override fun onFailure(call: Call<Registration>, t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message}", t)
        message.value = R.string.unable_to_connect
        progress.value = false
      }
    })
  }

  companion object {
    private const val TAG = "RegistrationViewModel"
  }
}