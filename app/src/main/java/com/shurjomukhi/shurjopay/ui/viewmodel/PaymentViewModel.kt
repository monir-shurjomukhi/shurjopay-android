package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.QrCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentViewModel(application: Application) : BaseViewModel(application) {
  private val _html = MutableLiveData<String>()
  val html: LiveData<String>
    get() = _html

  fun getHtml(qrData: String) {
    progress.value = true
    val qrCode = QrCode(qrData)
    apiClientQR?.getHtml(qrCode)?.enqueue(object : Callback<String>{
      override fun onResponse(call: Call<String>, response: Response<String>) {
        if (response.isSuccessful) {
          _html.value = response.body()
        } else {
          message.value = R.string.unable_to_connect
        }

        progress.value = false
      }

      override fun onFailure(call: Call<String>, t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message}", t)
        message.value = R.string.unable_to_connect
        progress.value = false
      }
    })
  }

  companion object {
    private const val TAG = "PaymentViewModel"
  }
}