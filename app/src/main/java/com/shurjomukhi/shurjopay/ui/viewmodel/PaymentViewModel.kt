package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.QrCode
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PaymentViewModel(application: Application) : BaseViewModel(application) {
  private val _html = MutableLiveData<String>()
  val html: LiveData<String>
    get() = _html

  fun getHtml(qrData: String) {
    viewModelScope.launch {
      progress.value = true

      val qrCode = QrCode(qrData)
      val response = try {
        apiClientQR.getHtml(qrCode)
      } catch (e: IOException) {
        Log.e(TAG, "register: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "register: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _html.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  companion object {
    private const val TAG = "PaymentViewModel"
  }
}