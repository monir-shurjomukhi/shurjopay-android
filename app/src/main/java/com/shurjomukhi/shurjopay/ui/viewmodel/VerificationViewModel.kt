package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.Otp
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class VerificationViewModel(application: Application) : BaseViewModel(application) {
  private val _otp = MutableLiveData<Otp>()
  val otp: LiveData<Otp>
    get() = _otp

  fun verifyOTP(otp: Otp) {
    viewModelScope.launch {
      progress.value = true

      val response = try {
        apiClient.verifyOTP(otp)
      } catch (e: IOException) {
        Log.e(TAG, "verifyOTP: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "verifyOTP: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _otp.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  fun verifyAccount(otp: Otp) {
    viewModelScope.launch {
      progress.value = true

      val response = try {
        apiClient.verifyAccount(otp)
      } catch (e: IOException) {
        Log.e(TAG, "verifyAccount: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "verifyAccount: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _otp.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  companion object {
    private const val TAG = "VerificationViewModel"
  }
}